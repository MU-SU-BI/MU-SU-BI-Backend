package com.musubi.domain.user.application;


import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.dto.UserLoginDto;
import com.musubi.domain.user.dto.UserSingUpDto;
import com.musubi.domain.user.exception.AlreadyExistEmailException;
import com.musubi.domain.user.exception.NotFoundUserException;
import com.musubi.domain.user.exception.WrongPasswordException;
import com.musubi.global.utils.ErrorMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    // TODO : 현재 구현된 회원가입과 로그인은 데모를 위한 그냥 동작 과정을 보여주기 위한 임시 구현이다 Spring security 를 이용한 구체적인 인증/인가에 대한 기능이 필요한 상황이다.

    private final UserRepository userRepository;

    public void signUpDemo(UserSingUpDto userSingUpDto) {

        if (userRepository.existsByEmail(userSingUpDto.getEmail())) {
            throw new AlreadyExistEmailException(ErrorMessage.ALREADY_EXIST_USER_ERROR.getErrorMessage());
        }

        User user = User.builder()
                .email(userSingUpDto.getEmail())
                .password(userSingUpDto.getPassword())
                .phoneNumber(userSingUpDto.getPhoneNumber())
                .homeAddress(userSingUpDto.getHomeAddress())
                .build();

        userRepository.save(user);
    }

    public void loginDemo(UserLoginDto userLoginDto) {

        Optional<User> user = userRepository.findByEmail(userLoginDto.getEmail());

        if (user.isEmpty()) {
            throw new NotFoundUserException(ErrorMessage.NOT_FOUND_USER_ERROR.getErrorMessage());
        }

        if (!user.get().validatePassword(userLoginDto.getPassword())) {
            throw new WrongPasswordException(ErrorMessage.WRONG_PASSWORD_ERROR.getErrorMessage());
        }
    }
}
