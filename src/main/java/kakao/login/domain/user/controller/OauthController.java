package kakao.login.domain.user.controller;

import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kakao.login.domain.user.dto.AuthorizationRequest;
import kakao.login.domain.user.dto.LoginResponse;
import kakao.login.domain.user.service.OauthService;
import kakao.login.global.dto.CustomResponse;
import kakao.login.global.exception.CustomException;
import kakao.login.global.exception.ErrorCode;
import kakao.login.global.jwt.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OauthController {

    private final OauthService oauthService;

    /**
     * OAuth 로그인 시 인증 코드를 넘겨받은 후 첫 로그인 시 회원가입
     */
    @Operation(summary = "OAuth 로그인 API", description = "코드를 받아와서 로그인 처리를 하는 API")
    @ApiResponse(responseCode = "200", description = "정상적으로 로그인 처리가 된 경우")
    @GetMapping("/login/oauth/{provider}")
    public LoginResponse login(@PathVariable String provider, @RequestParam String code) {
        log.info("In OauthController");
        return oauthService.login(new AuthorizationRequest(provider, code));
    }

}