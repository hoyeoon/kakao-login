package kakao.login.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorizationRequest {

    private String providerName;

    private String code;

    public AuthorizationRequest(String providerName, String code) {
        this.providerName = providerName;
        this.code = code;
    }
}
