package com.musubi.domain.notification.api;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.musubi.domain.notification.application.NotificationService;
import com.musubi.domain.notification.dto.HelpRequestDto;
import com.musubi.domain.notification.dto.SOSRequestDto;
import com.musubi.global.utils.DefaultResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/guardians") // guardians -> users 로 나중에 변경 해야할듯
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping("help") // url 나중에 변경
	public ResponseEntity<?> sendNotification(@RequestBody HelpRequestDto helpRequestDto)
		throws FirebaseMessagingException {
		notificationService.sendHelpNotification(helpRequestDto);
		return ResponseEntity.ok().body(new DefaultResponse(200, "FCM 메시지 전송 완료"));
	}

	@PostMapping("sos")
	public ResponseEntity<?> sendSOSNotificationInAllCommunityUsers(@RequestBody SOSRequestDto sosRequestDto) throws
		FirebaseMessagingException {
		notificationService.sendNotificationInAllCommunityUsers(sosRequestDto.getGuardianId());
		return ResponseEntity.ok().body(new DefaultResponse(200, "FCM 메시지 전송 완료"));
	}
}
