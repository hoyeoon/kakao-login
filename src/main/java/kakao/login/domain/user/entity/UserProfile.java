package kakao.login.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "nickName", "gender"})
public class UserProfile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Embedded
    private Address address;

    private String phoneNumber;

    @Enumerated(STRING)
    private Gender gender;

    @OneToOne(mappedBy = "userProfile", fetch = LAZY)
    private User user;

    private Integer age;

    private String provider;
    private String providerId;

    private String imageUrl;

    @Builder
    public UserProfile(String nickName, Address address, String phoneNumber, Gender gender, Integer age, String provider, String providerId) {
        this.nickName = nickName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
        this.provider = provider;
        this.providerId = providerId;
    }

    /**
     * 생성 메서드
     */
    public static UserProfile createProfile(String nickName, Gender gender,String provider,String providerId) {
        return UserProfile.builder()
                .nickName(nickName)
                .gender(gender)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }
}