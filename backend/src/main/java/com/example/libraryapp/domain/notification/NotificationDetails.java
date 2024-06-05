package com.example.libraryapp.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDetails {
    private LocalDateTime createdAt;
    private String reason;
    private String content;
    private String bookTitle;
    private String bookBarcode;
    private Long memberId;
    private String memberEmail;
    private String memberPhoneNumber;
}
