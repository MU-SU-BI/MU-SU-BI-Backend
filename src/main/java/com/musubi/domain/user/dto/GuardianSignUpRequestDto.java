package com.musubi.domain.user.dto;

import com.musubi.domain.user.type.SexType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GuardianSignUpRequestDto {
    @NotBlank(message = "이메일 필드는 필수입니다.")
    private final String email;

    @NotBlank(message = "비밀번호 필드는 필수입니다.")
    private final String password;

    @NotBlank(message = "이름 필드는 필수입니다.")
    private final String name;

    @NotBlank(message = "닉네임 필드는 필수입니다.")
    private final String nickname;

    @Min(value = 1, message = "나이는 1 이상의 값이어야 합니다.")
    private int age;

    @NotNull(message = "유효하지 않은 성별 입니다.")
    private SexType sex;

    @NotBlank(message = "전화번호 필드는 필수입니다.")
    private final String phoneNumber;

    @NotBlank(message = "자택 주소 필드는 필수입니다.")
    private final String homeAddress;
}
