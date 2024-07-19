package com.musubi.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ALREADY_EXIST_USER_ERROR("이미 존재하는 이메일 입니다.", 409),
    NOT_FOUND_USER_ERROR("존재하지 않는 유저입니다.", 401),
    WRONG_PASSWORD_ERROR("비밀번호가 일치하지 않습니다.", 401);

    private final String errorMessage;
    private final int httpStatusCode;

}
