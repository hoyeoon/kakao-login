package kakao.login.global.config.filter;

import kakao.login.domain.user.dto.LoginMember;
import kakao.login.domain.user.service.AuthService;
import kakao.login.global.exception.CustomException;
import kakao.login.global.jwt.AuthorizationExtractor;
import kakao.login.global.jwt.JwtTokenProvider;
import kakao.login.global.util.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("in JwtAuthenticationFilter");
        if (request.getMethod().equals("OPTIONS")) {
            return;
        }
        try {
            String accessToken = AuthorizationExtractor.extract(request);
            log.info("accessToken = {}", accessToken);
            if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
                LoginMember loginMember = authService.findMemberByToken(accessToken);
                log.info("loginMember = {}", loginMember.getId());
                UserAuthentication authentication = new UserAuthentication(loginMember.getId());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication = {}", authentication.getUserId());
            }
        } catch (CustomException e) {
            log.info("JwtAuthentication CustomException");
            request.setAttribute("CustomException", e);
        }
        chain.doFilter(request,response);
    }

}