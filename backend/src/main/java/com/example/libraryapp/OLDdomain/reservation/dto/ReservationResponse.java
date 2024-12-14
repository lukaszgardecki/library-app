package com.example.libraryapp.OLDdomain.reservation.dto;

import com.example.libraryapp.NEWdomain.bookItem.dto.BookItemDto;
import com.example.libraryapp.OLDdomain.member.dto.MemberDto;
import com.example.libraryapp.OLDdomain.reservation.ReservationStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse extends RepresentationModel<ReservationResponse> {
    private Long id;
    private LocalDateTime creationDate;
    private ReservationStatus status;
    private MemberDto member;
    private BookItemDto bookItem;
}