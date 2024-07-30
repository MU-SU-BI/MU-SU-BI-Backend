package com.musubi.domain.notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.musubi.domain.notification.dto.HelpRequestDto;
import com.musubi.domain.notification.type.FCMValue;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.global.constants.ErrorMessage;
import com.musubi.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final GuardianRepository guardianRepository;

    public void sendEmergencyMessageToGuardian() {

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
