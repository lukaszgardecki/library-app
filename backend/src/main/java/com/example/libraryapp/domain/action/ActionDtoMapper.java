package com.example.libraryapp.domain.action;

public class ActionDtoMapper {

    public static ActionDto map(Action action) {
        return ActionDto.builder()
                .id(action.getId())
                .memberId(action.getMemberId())
                .type(action.getType())
                .message(action.getMessage())
                .createdAt(action.getCreatedAt())
                .build();
    }
}