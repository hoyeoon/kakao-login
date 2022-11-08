package kakao.login.domain.user.service;

import kakao.login.domain.user.dto.UserResponse;
import kakao.login.domain.user.entity.User;
import kakao.login.domain.user.repository.UserRepository;
import kakao.login.global.dto.CustomResponse;
import kakao.login.global.exception.CustomException;
import kakao.login.global.exception.ErrorCode;
import kakao.login.global.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final Util util;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * 토큰으로 받아온 사용자 정보 넘겨주는 메서드
     */
    public User findCurrentUserId() {
        return util.findCurrentUser();
    }

    /**
     * 헤더에서 엑세스 토큰을 받아와서 회원 정보 및 회원 프로필이 수정 되어있는지 확인하고 반환하는 메서드
     */
    public UserResponse findValidateProfileSaveUser() {
        User findUser = findCurrentUserId();
        return UserResponse.builder()
                .id(findUser.getId())
                .nickName(findUser.getUserProfile().getNickName())
                .email(findUser.getEmail())
                .role(findUser.getRole())
                .address(findUser.getUserProfile().getAddress())
//                .imageUrl(findUser.getUserProfile().getUploadFile().getStoreFilename())
                .gender(findUser.getUserProfile().getGender())
                .phoneNumber(findUser.getUserProfile().getPhoneNumber())
                .age(findUser.getUserProfile().getAge())
                .build();
    }


    /**
     * 회원 프로필 단건 조회
     */
    public User findById(Long id) {
        return getFindById(id);
    }


    /**
     * 회원 탈퇴
     */
    @Transactional
    public CustomResponse deleteUser() {

        User findUser = findCurrentUserId();

        userRepository.delete(findUser);

        return new CustomResponse("회원 탈퇴가 완료 되었습니다.");

    }

    /**
     * 중복 로직 findById
     */
    private User getFindById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }

}