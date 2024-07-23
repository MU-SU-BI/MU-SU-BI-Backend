package com.musubi.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ALREADY_EXIST_EMAIL_ERROR("이미 존재하는 이메일 입니다."),
    ALREADY_EXIST_NICKNAME_ERROR("이미 존재하는 닉네임 입니다."),
    ALREADY_EXIST_PHONE_NUMBER_ERROR("이미 존재하는 전화번호 입니다."),
    NOT_FOUND_USER_ERROR("존재하지 않는 유저입니다."),
    WRONG_PASSWORD_ERROR("비밀번호가 일치하지 않습니다."),
    INPUT_INVALID_ERROR("올바른 입력값을 입력 해 주세요.");

    private final String errorMessage;
}
