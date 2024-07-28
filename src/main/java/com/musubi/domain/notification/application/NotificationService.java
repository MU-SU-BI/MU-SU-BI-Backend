package com.musubi.domain.notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.musubi.domain.notification.dto.HelpRequestDto;
import com.musubi.domain.notification.type.FCMValue;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public void sendEmergencyMessageToGuardian() {

    }

    public void sendHelpNotification(HelpRequestDto helpRequestDto) throws FirebaseMessagingException {

        Guardian guardian = userRepository.findById(helpRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("")).getGuardian();

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
