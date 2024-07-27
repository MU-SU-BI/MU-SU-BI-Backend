package com.musubi.domain.user.api;

import com.musubi.domain.user.application.GuardianService;
import com.musubi.domain.user.application.UserService;
import com.musubi.domain.user.dto.ConnectionRequestDto;
import com.musubi.domain.user.dto.GuardianLoginRequestDto;
import com.musubi.domain.user.dto.GuardianSignUpRequestDto;
import com.musubi.domain.user.dto.UserLoginRequestDto;
import com.musubi.domain.user.dto.UserSignUpRequestDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/guardians")
public class GuardianController {
    private final GuardianService guardianService;

    @PostMapping("signup")
    ResponseEntity<?> signUp(@RequestBody @Valid GuardianSignUpRequestDto guardianSignUpRequestDto) {
        guardianService.signUpDemo(guardianSignUpRequestDto);
        return ResponseEntity.status(201).body(new DefaultResponse(201, "회원가입 성공"));
    }

    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody @Valid GuardianLoginRequestDto guardianLoginRequestDto) {
        return ResponseEntity.status(200)
                .body(new DefaultDataResponse<>(200, "로그인 성공", guardianService.loginDemo(guardianLoginRequestDto)));
    }

    @PostMapping("connection")
    ResponseEntity<?> connection(@RequestBody @Valid ConnectionRequestDto connectionRequestDto) {
        guardianService.connection(connectionRequestDto);
        return ResponseEntity.status(200).body(new DefaultResponse(200, "연동 성공"));
    }
}
