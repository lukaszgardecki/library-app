package com.example.libraryapp.domain.useractivity.dto;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityDto {
    private Long id;
    private Long userId;
    private UserActivityType type;
    private String message;
    private LocalDateTime createdAt;
}
