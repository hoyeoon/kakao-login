package kakao.login.global.config.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import kakao.login.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("in EntryPoint");
        Object errorObject = request.getAttribute("CustomException");
        if (errorObject != null) {
            log.info("errorObject Not null");
            sendError(response, ErrorCode.UNAUTHORIZED_USER);
        }
        log.info("errorObject is null");
        sendError(response, ErrorCode.FORBIDDEN_USER);
    }

    private void sendError(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json,charset=utf-8");
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, SendErrorResponse.of(errorCode));
            os.flush();
        }
    }
}