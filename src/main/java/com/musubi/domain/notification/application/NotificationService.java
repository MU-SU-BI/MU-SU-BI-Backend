package com.musubi.domain.notification.application;

import java.util.List;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.musubi.domain.notification.dto.HelpRequestDto;
import com.musubi.domain.notification.type.FCMValue;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.global.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final GuardianRepository guardianRepository;

	public void sendLeftSafeAreaNotification(String fcmToken) throws FirebaseMessagingException {
		Notification notification = Notification.builder()
			.setTitle(FCMValue.ALERT.getTitle())
			.setBody(FCMValue.ALERT.getBody())
			.build();

		Message message = Message.builder()
			.setToken(fcmToken)
			.setNotification(notification)
			.build();

		firebaseMessaging.send(message);
	}

	public void sendNotificationInAllCommunityUsers(Long guardianId) throws FirebaseMessagingException {
		Guardian guardian = guardianRepository.findById(guardianId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		if (guardian.notHasLocation()) {
			throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
		}

		String locationDistrict = guardian.getLocationDistrict();

		List<Guardian> guardians = guardianRepository.findGuardianByLocation_District(locationDistrict);

		List<String> communityFcmTokens = guardians.stream().map(Guardian::getFcmToken).toList();

		System.out.println(communityFcmTokens);

		for (String fcmToken : communityFcmTokens) {

			if (fcmToken == null || guardian.getFcmToken() == null) {
				continue;
			}

			if (fcmToken.equals(guardian.getFcmToken())) {
				continue;
			}

			System.out.println(fcmToken);

			Notification notification = Notification.builder()
				.setTitle(FCMValue.SOS.getTitle())
				.setBody(FCMValue.SOS.getBody())
				.build();

			Message message = Message.builder()
				.setToken(fcmToken)
				.setNotification(notification)
				.build();

			firebaseMessaging.send(message);
		}
	}

	public void sendHelpNotification(HelpRequestDto helpRequestDto) throws FirebaseMessagingException {

		Guardian guardian = guardianRepository.findByUserId(helpRequestDto.getUserId()).orElseThrow(
			() -> new BusinessLogicException("존재하지 않는 보호자 입니다.", HttpStatus.NOT_FOUND.value()));

		Notification notification = Notification.builder()
			.setTitle(FCMValue.HELP.getTitle())
			.setBody(helpRequestDto.getCallMessage())
			.build();

		Message message = Message.builder()
			.setToken(guardian.getFcmToken())
			.setNotification(notification)
			.build();

		firebaseMessaging.send(message);
	}
}
