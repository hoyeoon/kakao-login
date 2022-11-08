package kakao.login.global.config.provider;

import kakao.login.domain.user.entity.Gender;

public interface Oauth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getNickName();
    Gender getGender();
    String getImageUrl();
}