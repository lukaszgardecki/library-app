package com.example.libraryapp.NEWdomain.useractivity.dto;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
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
