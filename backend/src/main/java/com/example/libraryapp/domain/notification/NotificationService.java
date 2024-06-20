package com.example.libraryapp.domain.notification;

import com.example.libraryapp.domain.action.ActionRepository;
import com.example.libraryapp.domain.action.types.NotificationSentEmailAction;
import com.example.libraryapp.domain.action.types.NotificationSentSmsAction;
import com.example.libraryapp.domain.action.types.NotificationSentSystemAction;
import com.example.libraryapp.domain.config.assembler.NotificationModelAssembler;
import com.example.libraryapp.domain.exception.notification.NotificationNotFoundException;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.notification.dto.NotificationDto;
import com.example.libraryapp.domain.notification.strategies.EmailNotificationStrategy;
import com.example.libraryapp.domain.notification.strategies.SmsNotificationStrategy;
import com.example.libraryapp.domain.notification.strategies.SystemNotificationStrategy;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ActionRepository actionRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationModelAssembler notificationModelAssembler;
    private final PagedResourcesAssembler<Notification> pagedResourcesAssembler;
    private static final String TOPIC = "notifications";

    public PagedModel<NotificationDto> findNotifications(Long memberId, Pageable pageable) {
        List<Notification> notifications = notificationRepository.findAll().stream()
                .filter(notification -> memberId == null || Objects.equals(notification.getMemberId(), memberId))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), notifications.size());
        List<Notification> paginatedList = notifications.subList(start, end);
        Page<Notification> notificationPage = new PageImpl<>(paginatedList, pageable, notifications.size());
        return pagedResourcesAssembler.toModel(notificationPage, notificationModelAssembler);
    }

    public NotificationDto findNotificationById(Long id) {
        Notification notification = findNotification(id);
        return notificationModelAssembler.toModel(notification);
    }

    public void saveAndSendNotification(NotificationType notificationType, ReservationResponse reservation) {
        Notification notificationToSave = new Notification(notificationType);
        notificationToSave.setMemberId(reservation.getMember().getId());
        notificationToSave.setBookTitle(reservation.getBookItem().getBook().getTitle());
        notificationToSave.setSubject(notificationType.getReason());
        notificationToSave.setContent(notificationType.getContent());
        Notification savedNotification = notificationRepository.save(notificationToSave);
        NotificationDto notification = NotificationDtoMapper.map(savedNotification);
        sendTo(reservation.getMember(), notification);
    }

    public void saveAndSendNotification(NotificationType notificationType, LendingDto lending) {
        Notification notificationToSave = new Notification(notificationType);
        notificationToSave.setMemberId(lending.getMember().getId());
        notificationToSave.setBookTitle(lending.getBookItem().getBook().getTitle());
        notificationToSave.setSubject(notificationType.getReason());
        notificationToSave.setContent(notificationType.getContent());
        Notification savedNotification = notificationRepository.save(notificationToSave);
        NotificationDto notification = NotificationDtoMapper.map(savedNotification);
        sendTo(lending.getMember(), notification);
    }

    @Transactional
    public void markAsRead(Long id) {
        Notification notification = findNotification(id);
        notification.setRead(true);
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

    private void sendTo(MemberDto member, NotificationDto content) {
        sendSystemNotification(content);
        sendEmailNotification(member, content);
        sendSmsNotification(member, content);
    }

    private void sendSystemNotification(NotificationDto notification) {
        new SystemNotificationStrategy(messagingTemplate).send(notification);
        actionRepository.save(new NotificationSentSystemAction(notification));
    }

    private void sendEmailNotification(MemberDto member, NotificationDto notification) {
        new EmailNotificationStrategy(member.getEmail()).send(notification);
        actionRepository.save(new NotificationSentEmailAction(notification));
    }

    private void sendSmsNotification(MemberDto member, NotificationDto notification) {
        new SmsNotificationStrategy(member.getPhoneNumber()).send(notification);
        actionRepository.save(new NotificationSentSmsAction(notification));
    }

    private Notification findNotification(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }
}
