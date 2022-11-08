package kakao.login.global.config.filter;

import kakao.login.global.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendErrorResponse {

    private int status;
    private String code;
    private String message;

    public SendErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus().value();
        this.code = errorCode.name();
        this.message = errorCode.getDetail();
    }

    public static SendErrorResponse of(ErrorCode errorCode) {
        return new SendErrorResponse(errorCode);
    }
}