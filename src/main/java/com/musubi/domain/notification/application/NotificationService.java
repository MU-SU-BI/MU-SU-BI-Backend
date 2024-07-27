package com.musubi.domain.notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.musubi.domain.notification.dto.FCMRequestDto;
import com.musubi.domain.notification.type.FCMValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendEmergencyMessageToGuardian() {

    }

    public void sendNotificationByToken(FCMRequestDto fcmRequestDto) throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle(FCMValue.HELP.getTitle())
                .setBody(FCMValue.HELP.getBody())
                .build();

        Message message = Message.builder()
                .setToken(fcmRequestDto.getTargetToken())
                .setNotification(notification)
                .build();

        firebaseMessaging.send(message);

    }
}
