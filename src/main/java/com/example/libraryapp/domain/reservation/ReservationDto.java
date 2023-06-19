package com.example.libraryapp.domain.reservation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto extends RepresentationModel<ReservationDto> {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userCardNumber;
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private Integer bookReleaseYear;
    private Integer bookPages;
    private String bookIsbn;
}