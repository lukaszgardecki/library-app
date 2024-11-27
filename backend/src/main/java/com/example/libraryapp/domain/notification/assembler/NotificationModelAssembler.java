package com.example.libraryapp.domain.notification.assembler;

import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.domain.notification.NotificationDtoMapper;
import com.example.libraryapp.domain.notification.dto.NotificationDto;
import com.example.libraryapp.web.NotificationController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class NotificationModelAssembler extends RepresentationModelAssemblerSupport<Notification, NotificationDto> {


    public NotificationModelAssembler() {
        super(NotificationController.class, NotificationDto.class);
    }

    @Override
    @NonNull
    public NotificationDto toModel(@NonNull Notification entity) {
        NotificationDto dto = NotificationDtoMapper.map(entity);
        dto.add(linkTo(methodOn(NotificationController.class).getNotificationById(entity.getId())).withSelfRel());
        if (entity.getMemberId() != null) {
            dto.add(linkTo(methodOn(NotificationController.class).getAllNotifications(entity.getMemberId(), null)).withRel(IanaLinkRelations.COLLECTION));
        }
        return dto;
    }

    @Override
    @NonNull
    public CollectionModel<NotificationDto> toCollectionModel(@NonNull Iterable<? extends Notification> entities) {
        CollectionModel<NotificationDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(NotificationController.class).getAllNotifications(null, null)).withSelfRel());
        return collectionModel;
    }
}
