package com.musubi.domain.notification.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FCMValue {
    EMERGENCY("긴급", "피 보호자가 응급 상황입니다."),
    HELP("도움", "피 보호자가 도움이 필요한 상황입니다.");

    private final String title;
    private final String body;
}
