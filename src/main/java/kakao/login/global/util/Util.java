package kakao.login.global.util;

import kakao.login.domain.user.entity.User;
import kakao.login.domain.user.repository.UserRepository;
import kakao.login.global.exception.CustomException;
import kakao.login.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Util {
    private final UserRepository userRepository;

    public User findCurrentUser() {

        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }
}