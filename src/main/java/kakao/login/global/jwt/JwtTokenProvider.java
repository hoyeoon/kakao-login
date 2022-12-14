package kakao.login.global.jwt;

import io.jsonwebtoken.*;
import kakao.login.global.exception.CustomException;
import kakao.login.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMilliseconds;

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    public Token createAccessToken(String payload) {
        log.info("createToken In accessToken = {}", accessTokenValidityInMilliseconds);
        log.info("createToken In secretKey = {}", secretKey);
        String token = createToken(payload,accessTokenValidityInMilliseconds);
        return new Token(token, accessTokenValidityInMilliseconds);
    }

    public Token createRefreshToken() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        String token = createToken(generatedString, refreshTokenValidityInMilliseconds);
        return new Token(token, refreshTokenValidityInMilliseconds);
    }

    public String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload);
        log.info("claims = {}", claims);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);
        log.info("validity = {}", validity);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public String getPayload(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e){
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}