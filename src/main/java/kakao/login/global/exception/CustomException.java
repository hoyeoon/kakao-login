package kakao.login.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}