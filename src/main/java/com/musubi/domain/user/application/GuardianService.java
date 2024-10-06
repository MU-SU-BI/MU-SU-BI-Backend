package com.musubi.domain.user.application;


import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.dto.ConnectionRequestDto;
import com.musubi.domain.user.dto.ConnectionResponseDto;
import com.musubi.domain.user.dto.GuardianLoginRequestDto;
import com.musubi.domain.user.dto.GuardianLoginResponseDto;
import com.musubi.domain.user.dto.GuardianProfileRequestDto;
import com.musubi.domain.user.dto.GuardianSignUpRequestDto;
import com.musubi.domain.user.dto.UserResponseDto;
import com.musubi.global.exception.BusinessLogicException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GuardianService {
    // TODO : 현재 구현된 회원가입과 로그인은 데모를 위한 그냥 동작 과정을 보여주기 위한 임시 구현이다 Spring security 를 이용한 구체적인 인증/인가에 대한 기능이 필요한 상황이다.

    private final GuardianRepository guardianRepository;
    private final UserRepository userRepository;

    public void signUpDemo(GuardianSignUpRequestDto guardianSignUpRequestDto) {

        checkDuplicateEmail(guardianSignUpRequestDto.getEmail());
        checkDuplicateNickname(guardianSignUpRequestDto.getNickname());
        checkDuplicatePhoneNumber(guardianSignUpRequestDto.getPhoneNumber());

        Guardian guardian = Guardian.builder()
                .email(guardianSignUpRequestDto.getEmail())
                .password(guardianSignUpRequestDto.getPassword())
                .name(guardianSignUpRequestDto.getName())
                .nickname(guardianSignUpRequestDto.getNickname())
                .age(guardianSignUpRequestDto.getAge())
                .sex(guardianSignUpRequestDto.getSex())
                .phoneNumber(guardianSignUpRequestDto.getPhoneNumber())
                .homeAddress(guardianSignUpRequestDto.getHomeAddress())
                .build();

        guardianRepository.save(guardian);
    }


    @Transactional
    public GuardianLoginResponseDto loginDemo(GuardianLoginRequestDto guardianLoginRequestDto) {

        Guardian guardian = guardianRepository.findByEmail(guardianLoginRequestDto.getEmail())
                .orElseThrow(() -> new BusinessLogicException("존재하지 않는 이메일 입니다.", HttpStatus.UNAUTHORIZED.value()));

        guardian.updateFcmDeviceToken(guardianLoginRequestDto.getFcmToken());

        if (!guardian.validatePassword(guardianLoginRequestDto.getPassword())) {
            throw new BusinessLogicException("옳지않은 비밀번호 입니다.", HttpStatus.UNAUTHORIZED.value());
        }

        return GuardianLoginResponseDto.fromEntity(guardian);
    }

    @Transactional
    public ConnectionResponseDto connection(ConnectionRequestDto connectionRequestDto) {
        User user = userRepository.findByNameAndPhoneNumber(connectionRequestDto.getDisabledName(),
                        connectionRequestDto.getDisabledPhoneNumber())
                .orElseThrow(() -> new BusinessLogicException("해당 하는 User가 존재하지 않습니다", HttpStatus.BAD_REQUEST.value()));

        Guardian guardian = guardianRepository.findById(connectionRequestDto.getUserId())
                .orElseThrow(() -> new BusinessLogicException("존재 하지 않은 보호자 입니다.", HttpStatus.BAD_REQUEST.value()));

        guardian.connectUser(user);
        return ConnectionResponseDto.fromEntity(user);
    }

    @Transactional
    public void uploadProfile(MultipartFile image, Long userId) {

        if (image == null)
            throw new BusinessLogicException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value());

        Guardian guardian = guardianRepository.findById(userId)
                .orElseThrow(()-> new BusinessLogicException("존재 하지 않는 보호자 입니다.",HttpStatus.BAD_REQUEST.value()));

        if (guardian.getUser() == null)
            throw new BusinessLogicException("연동된 User가 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        else {
            User user = guardian.getUser();
            try {
                user.updateProfile(image.getBytes());
            } catch (IOException e) {
                throw new BusinessLogicException("파일이 올바르지 않습니다.", HttpStatus.BAD_REQUEST.value());
            }
        }
    }

    @Transactional
    public UserResponseDto findMyUserById(Long guardianId) {

        User user = userRepository.findByGuardianId(guardianId)
                .orElseThrow(() -> new BusinessLogicException("아직 등록된 장애인이 없습니다.", HttpStatus.BAD_REQUEST.value()));

        return UserResponseDto.fromEntity(user);
    }


    private void checkDuplicateEmail(String inputEmail) {
        guardianRepository.findByEmail(inputEmail).ifPresent((user) -> {
            throw new BusinessLogicException("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST.value());
        });
    }

    private void checkDuplicateNickname(String inputNickname) {
        guardianRepository.findByNickname(inputNickname).ifPresent((user) -> {
            throw new BusinessLogicException("이미 존재하는 닉네임 입니다.", HttpStatus.BAD_REQUEST.value());
        });
    }

    private void checkDuplicatePhoneNumber(String inputPhoneNumber) {
        guardianRepository.findByPhoneNumber(inputPhoneNumber).ifPresent((user) -> {
            throw new BusinessLogicException("이미 존재하는 휴대폰 번호 입니다.", HttpStatus.BAD_REQUEST.value());
        });
    }
}

