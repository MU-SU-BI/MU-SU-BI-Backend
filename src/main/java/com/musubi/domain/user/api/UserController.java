package com.musubi.domain.user.api;

import com.musubi.domain.user.application.UserService;
import com.musubi.domain.user.dto.UserLoginRequestDto;
import com.musubi.domain.user.dto.UserSignUpRequestDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    ResponseEntity<?> signUp(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        userService.signUpDemo(userSignUpRequestDto);
        return ResponseEntity.status(201).body(new DefaultResponse(201, "회원가입 성공"));
    }

    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.status(200)
                .body(new DefaultDataResponse<>(200, "로그인 성공", userService.loginDemo(userLoginRequestDto)));
    }

    @GetMapping("{userId}/guardian")
    ResponseEntity<?> findMyGuardian(@PathVariable Long userId) {
        return ResponseEntity.ok()
                .body(new DefaultDataResponse<>(200, "내 보호자 조회 성공", userService.findMyGuardianByUserId(userId)));
    }
}
