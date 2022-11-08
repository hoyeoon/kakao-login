package kakao.login.domain.user.service;

import kakao.login.domain.user.dto.AuthorizationRequest;
import kakao.login.domain.user.dto.LoginResponse;
import kakao.login.domain.user.dto.OauthTokenResponse;
import kakao.login.domain.user.entity.User;
import kakao.login.domain.user.oauth.OauthAttributes;
import kakao.login.domain.user.repository.UserRepository;
import kakao.login.global.jwt.JwtTokenProvider;
import kakao.login.global.jwt.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * @InMemoryRepository application-oauth properties 정보를 담고 있음
     * @getToken() 넘겨받은 code 로 Oauth 서버에 Token 요청
     * @getUserProfile 첫 로그인 시 회원가입
     * 유저 인증 후 Jwt AccessToken, Refresh Token 생성
     * TODO REDIS에 Refresh Token 저장
     */
    @Transactional
    public LoginResponse login(AuthorizationRequest authorizationRequest) {

        log.info("in OauthService");
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(authorizationRequest.getProviderName());
        log.info("provider.getClientId = {}", provider.getClientId());

        User user = getUserProfile(authorizationRequest, provider);

        log.info("user = {}", user);

        Token accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        log.info("accessToken = {}", accessToken.getValue());
        Token refreshToken = jwtTokenProvider.createRefreshToken();
        log.info("refreshToken = {}", refreshToken.getValue());

        return LoginResponse.builder()
                .id(user.getId())
                .nickName(user.getUserProfile().getNickName())
                .email(user.getEmail())
                .imageUrl(user.getUserProfile().getImageUrl())
                .role(user.getRole())
                .tokenType(BEARER_TYPE)
                .accessToken(accessToken.getValue())
                .refreshToken(refreshToken.getValue())
                .build();
    }

    private User getUserProfile(AuthorizationRequest authorizationRequest, ClientRegistration provider) {
        OauthTokenResponse token = getToken(authorizationRequest, provider);
        Map<String, Object> userAttributes = getUserAttributes(provider, token);
        User extract = OauthAttributes.extract(authorizationRequest.getProviderName(), userAttributes);
        return saveOrUpdate(extract);
    }

    /**
     * 저장, 변경 메서드
     * Todo update 로직
     */
    private User saveOrUpdate(User user) {
        User findUser = userRepository.findByOauthId(user.getUserProfile().getProviderId());
        if (findUser == null) {
            findUser = userRepository.save(user);
        }
        return findUser;

    }

    private OauthTokenResponse getToken(AuthorizationRequest authorizationRequest, ClientRegistration provider) {
        log.info("OauthService.getToken In");
        log.info("provider.TokenUri = {}" , provider.getProviderDetails().getTokenUri());
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                    log.info("header = {}", header);
                })
                .bodyValue(tokenRequest(authorizationRequest, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();

    }

    private MultiValueMap<String, String> tokenRequest(AuthorizationRequest authorizationRequest, ClientRegistration provider) {
        log.info("tokenRequest In");
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", authorizationRequest.getCode());
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret",provider.getClientSecret());
        formData.add("client_id",provider.getClientId());
        log.info("redirectUri = {}", provider.getRedirectUri());
        return formData;
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, OauthTokenResponse tokenResponse) {
        log.info("getUserAttributes In");
        log.info("userinfoUri = {}", provider.getProviderDetails().getUserInfoEndpoint().getUri());
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
//
//    private boolean validateProfileSaveUser(Long id) {
//        UserProfileQueryDto findUser = userRepository.findUserProfileById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
//
//        if (findUser.getImgUrl() == null || findUser.getNickName() == null || findUser.getGender() == null || findUser.getAddress() == null ||
//                findUser.getEmail() == null || findUser.getPhoneNumber() == null) {
//            return false;
//        }
//        return true;
//    }
}