package com.musubi.domain.user.application;


import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.dto.GuardianResponseDto;
import com.musubi.domain.user.dto.UserLoginRequestDto;
import com.musubi.domain.user.dto.UserLoginResponseDto;
import com.musubi.domain.user.dto.UserSignUpRequestDto;
import com.musubi.global.exception.BusinessLogicException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    // TODO : 현재 구현된 회원가입과 로그인은 데모를 위한 그냥 동작 과정을 보여주기 위한 임시 구현이다 Spring security 를 이용한 구체적인 인증/인가에 대한 기능이 필요한 상황이다.

    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;

    public void signUpDemo(UserSignUpRequestDto userSignUpRequestDto) {

        checkDuplicateEmail(userSignUpRequestDto.getEmail());
        checkDuplicateNickname(userSignUpRequestDto.getNickname());
        checkDuplicatePhoneNumber(userSignUpRequestDto.getPhoneNumber());

        User user = User.builder()
                .email(userSignUpRequestDto.getEmail())
                .password(userSignUpRequestDto.getPassword())
                .name(userSignUpRequestDto.getName())
                .nickname(userSignUpRequestDto.getNickname())
                .age(userSignUpRequestDto.getAge())
                .sex(userSignUpRequestDto.getSex())
                .phoneNumber(userSignUpRequestDto.getPhoneNumber())
                .homeAddress(userSignUpRequestDto.getHomeAddress())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public UserLoginResponseDto loginDemo(UserLoginRequestDto userLoginRequestDto) {

        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new BusinessLogicException("존재하지 않는 이메일 입니다.", HttpStatus.UNAUTHORIZED.value()));

        user.updateFcmDeviceToken(userLoginRequestDto.getFcmToken());

        if (!user.validatePassword(userLoginRequestDto.getPassword())) {
            throw new BusinessLogicException("옳지않은 비밀번호 입니다.", HttpStatus.UNAUTHORIZED.value());
        }

        return UserLoginResponseDto.fromEntity(user);
    }

    public GuardianResponseDto findMyGuardianByUserId(Long userId) {

        Guardian guardian = guardianRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessLogicException("아직 등록된 보호자가 없습니다.", HttpStatus.BAD_REQUEST.value()));

        return GuardianResponseDto.fromEntity(guardian);
    }


    private void checkDuplicateEmail(String inputEmail) {
        userRepository.findByEmail(inputEmail).ifPresent((user) -> {
            throw new BusinessLogicException("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST.value());
        });
    }

    private void checkDuplicateNickname(String inputNickname) {
        userRepository.findByNickname(inputNickname).ifPresent((user) -> {
            throw new BusinessLogicException("이미 존재하는 닉네임 입니다.", HttpStatus.BAD_REQUEST.value());
        });
    }

    private void checkDuplicatePhoneNumber(String inputPhoneNumber) {
        userRepository.findByPhoneNumber(inputPhoneNumber).ifPresent((user) -> {
            throw new BusinessLogicException("이미 존재하는 휴대폰 번호 입니다.", HttpStatus.BAD_REQUEST.value());
        });
    }
}
