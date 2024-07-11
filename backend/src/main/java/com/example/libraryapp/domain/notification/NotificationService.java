package com.example.libraryapp.domain.notification;

import com.example.libraryapp.domain.action.ActionService;
import com.example.libraryapp.domain.action.types.ActionNotificationSentEmail;
import com.example.libraryapp.domain.action.types.ActionNotificationSentSms;
import com.example.libraryapp.domain.action.types.ActionNotificationSentSystem;
import com.example.libraryapp.domain.config.assembler.NotificationModelAssembler;
import com.example.libraryapp.domain.exception.notification.NotificationNotFoundException;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.notification.dto.NotificationDto;
import com.example.libraryapp.domain.notification.strategies.EmailNotificationStrategy;
import com.example.libraryapp.domain.notification.strategies.SmsNotificationStrategy;
import com.example.libraryapp.domain.notification.strategies.SystemNotificationStrategy;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ActionService actionService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationModelAssembler notificationModelAssembler;
    private final PagedResourcesAssembler<Notification> pagedResourcesAssembler;
    private static final String TOPIC = "notifications";

    public PagedModel<NotificationDto> findNotifications(Long memberId, Pageable pageable) {
        Page<Notification> notificationPage = notificationRepository.findAllByParams(memberId, pageable);
        return pagedResourcesAssembler.toModel(notificationPage, notificationModelAssembler);
    }

    public NotificationDto findNotificationById(Long id) {
        Notification notification = findNotification(id);
        return notificationModelAssembler.toModel(notification);
    }

    public void sendToUser(Notification notification, MemberDto user) {
        Notification savedNotification = notificationRepository.save(notification);
        sendNotificationToUser(savedNotification, user);
    }

    @Transactional
    public void markAsRead(Long id) {
        Notification notification = findNotification(id);
        notification.setIsRead(true);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public void deleteNotifications(List<Long> ids, Long userId, boolean isAdmin) {
        List<Notification> notificationsToDelete = isAdmin
                ? notificationRepository.findAllById(ids)
                : notificationRepository.findAllById(ids).stream()
                    .filter(notification -> notification.getMemberId().equals(userId))
                    .toList();
        notificationRepository.deleteAll(notificationsToDelete);
    }

    public void sendToWarehouse(ReservationResponse reservation) {
        messagingTemplate.convertAndSend("/queue/warehouse", reservation);
    }

    private void sendNotificationToUser(Notification notification, MemberDto user) {
        NotificationDto notificationDto = NotificationDtoMapper.map(notification);
        sendSystemNotification(notificationDto);
        sendEmailNotification(user.getEmail(), notificationDto);
        sendSmsNotification(user.getPhoneNumber(), notificationDto);
    }

    private void sendSystemNotification(NotificationDto notification) {
        new SystemNotificationStrategy(messagingTemplate).send(notification);
        actionService.save(new ActionNotificationSentSystem(notification));
    }

    private void sendEmailNotification(String email, NotificationDto notification) {
        new EmailNotificationStrategy(email).send(notification);
        actionService.save(new ActionNotificationSentEmail(notification));
    }

    private void sendSmsNotification(String phoneNumber, NotificationDto notification) {
        new SmsNotificationStrategy(phoneNumber).send(notification);
        actionService.save(new ActionNotificationSentSms(notification));
    }

    private Notification findNotification(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }
}
