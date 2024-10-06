package com.musubi.domain.notification.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FCMValue {
	EMERGENCY("긴급", "피 보호자가 응급 상황입니다."),
	HELP("도움", "피 보호자가 도움이 필요한 상황입니다."),
	SOS("SOS", "주변 커뮤니티에 도움이 필요한 사람이 있습니다."),
	ALERT("알림", "피 보호자가 안전구역을 벗어 났습니ㅏ.");

	private final String title;
	private final String body;
}
