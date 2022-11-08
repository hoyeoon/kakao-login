package kakao.login.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kakao.login.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import static kakao.login.domain.user.entity.QUser.user;
import static kakao.login.domain.user.entity.QUserProfile.userProfile;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public User findByOauthId(String providerId) {
        return queryFactory
                .selectFrom(user)
                .innerJoin(user.userProfile, userProfile)
                .fetchJoin()
                .where(userProfile.providerId.eq(providerId))
                .fetchOne();
    }
}