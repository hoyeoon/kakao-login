package kakao.login.domain.user.service;

import kakao.login.domain.user.dto.LoginMember;
import kakao.login.domain.user.entity.User;
import kakao.login.global.exception.CustomException;
import kakao.login.global.exception.ErrorCode;
import kakao.login.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    /**
     * access token 이 유효한지 확인
     */
    public void validateAccessToken(String accessToken) {
        accessTokenExtractor(accessToken);
    }



    /**
     * 토큰으로 회원 조회
     */
    @Transactional(readOnly = true)
    public LoginMember findMemberByToken(String accessToken) {
        if (!accessToken.isEmpty()) {
            accessTokenExtractor(accessToken);
        }

        Long id = Long.parseLong(jwtTokenProvider.getPayload(accessToken));
        User findUser = userService.findById(id);
        return new LoginMember(findUser.getId());
    }

    /**
     * AccessToken 검증 메서드
     */
    private void accessTokenExtractor(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS_TOKEN);
        }
    }
}