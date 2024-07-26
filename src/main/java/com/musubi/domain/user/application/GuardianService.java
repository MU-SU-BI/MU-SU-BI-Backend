package com.musubi.domain.user.application;


import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.dto.GuardianLoginRequestDto;
import com.musubi.domain.user.dto.GuardianLoginResponseDto;
import com.musubi.domain.user.dto.GuardianSignUpRequestDto;

import com.musubi.domain.user.exception.AlreadyExistEmailException;
import com.musubi.domain.user.exception.AlreadyExistNicknameException;
import com.musubi.domain.user.exception.AlreadyExistPhoneNumberException;
import com.musubi.domain.user.exception.NotFoundUserException;
import com.musubi.domain.user.exception.WrongPasswordException;
import com.musubi.global.constants.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardianService {
    // TODO : 현재 구현된 회원가입과 로그인은 데모를 위한 그냥 동작 과정을 보여주기 위한 임시 구현이다 Spring security 를 이용한 구체적인 인증/인가에 대한 기능이 필요한 상황이다.

    private final GuardianRepository guardianRepository;

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

    public GuardianLoginResponseDto loginDemo(GuardianLoginRequestDto guardianLoginRequestDto) {

        Guardian guardian = guardianRepository.findByEmail(guardianLoginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundUserException(ErrorMessage.NOT_FOUND_USER_ERROR.getErrorMessage()));

        if (!guardian.validatePassword(guardianLoginRequestDto.getPassword())) {
            throw new WrongPasswordException(ErrorMessage.WRONG_PASSWORD_ERROR.getErrorMessage());
        }

        return GuardianLoginResponseDto.fromEntity(guardian);
    }

    private void checkDuplicateEmail(String inputEmail) {
        guardianRepository.findByEmail(inputEmail).ifPresent((user) -> {
            throw new AlreadyExistEmailException(ErrorMessage.ALREADY_EXIST_EMAIL_ERROR.getErrorMessage());
        });
    }

    private void checkDuplicateNickname(String inputNickname) {
        guardianRepository.findByNickname(inputNickname).ifPresent((user) -> {
            throw new AlreadyExistNicknameException(ErrorMessage.ALREADY_EXIST_NICKNAME_ERROR.getErrorMessage());
        });
    }

    private void checkDuplicatePhoneNumber(String inputPhoneNumber) {
        guardianRepository.findByPhoneNumber(inputPhoneNumber).ifPresent((user) -> {
            throw new AlreadyExistPhoneNumberException(ErrorMessage.ALREADY_EXIST_PHONE_NUMBER_ERROR.getErrorMessage());
        });
    }
}

