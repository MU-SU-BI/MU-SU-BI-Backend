package com.musubi.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserSignUpRequestDto {

    @NotBlank(message = "이메일 필드는 필수입니다.")
    private final String email;
    @NotBlank(message = "비밀번호 필드는 필수입니다.")
    private final String password;
    @NotBlank(message = "닉네임 필드는 필수입니다.")
    private final String nickname;
    @NotBlank(message = "전화번호 필드는 필수입니다.")
    private final String phoneNumber;
    @NotBlank(message = "집 주소 필드는 필수입니다.")
    private final String homeAddress;
}
