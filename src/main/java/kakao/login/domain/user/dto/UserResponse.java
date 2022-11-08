package kakao.login.domain.user.dto;

import kakao.login.domain.user.entity.Address;
import kakao.login.domain.user.entity.Gender;
import kakao.login.domain.user.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private Role role;
    private String nickName;
    private Address address;
    private String imageUrl;
    private Gender gender;
    private String phoneNumber;
    private Integer age;
    private Boolean profileSaveUser;

    @Builder
    public UserResponse(
            Long id, String email, Role role,
            String nickName, Address address, String imageUrl,
            Gender gender, String phoneNumber, Integer age,
            Boolean profileSaveUser) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.nickName = nickName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.profileSaveUser = profileSaveUser;
    }
}

