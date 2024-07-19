package com.musubi.domain.user.api;

import com.musubi.domain.user.application.UserService;
import com.musubi.domain.user.dto.UserLoginDto;
import com.musubi.domain.user.dto.UserSingUpDto;
import com.musubi.global.utils.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    ResponseEntity<?> signUp(@RequestBody UserSingUpDto userSingUpDto) {
        userService.signUpDemo(userSingUpDto);
        return ResponseEntity.status(201).body(new DefaultResponse(201, "회원가입 성공"));
    }

    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        userService.loginDemo(userLoginDto);
        return ResponseEntity.status(200).body(new DefaultResponse(200, "로그인 성공"));
    }
}
