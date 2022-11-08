package kakao.login.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_REQUEST_POST(HttpStatus.BAD_REQUEST, "글 입력값을 다시 확인하세요."),
    BAD_REQUEST_COMMENT(HttpStatus.BAD_REQUEST, "댓글 입력값을 다시 확인하세요."),
    BAD_REQUEST_LIKE(HttpStatus.BAD_REQUEST, "자신의 글에 좋아요를 누를 수 없습니다"),
    BAD_REQUEST_NICKNAME(HttpStatus.BAD_REQUEST,"2~10 자리 공백 제외 다시 입력해 주세요"),
    BAD_REQUEST_PROFILE(HttpStatus.BAD_REQUEST, "글 입력값을 다시 확인하세요."),
    BAD_REQUEST_Token(HttpStatus.BAD_REQUEST, "토큰 입력값을 다시 확인하세요."),

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),
    FORBIDDEN_USER(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다"),

    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 글을 찾을 수 없습니다"),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다"),
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "해당 태그를 찾을 수 없습니다"),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),

    UNAUTHORIZED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access token이 유효하지 않습니다."),
    UNAUTHORIZED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh token이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}