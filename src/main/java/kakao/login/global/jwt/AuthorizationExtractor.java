package kakao.login.global.jwt;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        log.info("headers = {}", headers);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            log.info("value = {}", value);
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                log.info("authHeaderValue = {}", authHeaderValue);
                return authHeaderValue;
            }
        }
        return null;
    }
}