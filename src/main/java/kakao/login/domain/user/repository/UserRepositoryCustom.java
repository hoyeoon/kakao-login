package kakao.login.domain.user.repository;

import kakao.login.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    User findByOauthId(String providerId);
}