package kakao.login.domain.user.dto;

import kakao.login.domain.user.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String nickName;
    private String email;
    private String imageUrl;
    private Role role;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    boolean ProfileSaveUser;

    @Builder
    public LoginResponse(Long id, String nickName, String email, String imageUrl, Role role, String tokenType, String accessToken, String refreshToken, boolean profileSaveUser) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.ProfileSaveUser = profileSaveUser;
    }
}