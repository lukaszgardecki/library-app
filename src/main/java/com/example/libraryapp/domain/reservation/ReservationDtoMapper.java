package com.example.libraryapp.domain.reservation;

public class ReservationDtoMapper {

    public static ReservationDto map(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setUserId(reservation.getUser().getId());
        dto.setUserFirstName(reservation.getUser().getFirstName());
        dto.setUserLastName(reservation.getUser().getLastName());
        dto.setUserEmail(reservation.getUser().getEmail());
        dto.setUserCard(reservation.getUser().getCard());
        dto.setBookId(reservation.getBook().getId());
        dto.setBookTitle(reservation.getBook().getTitle());
        dto.setBookAuthor(reservation.getBook().getAuthor());
        dto.setBookPublisher(reservation.getBook().getPublisher());
        dto.setBookReleaseYear(reservation.getBook().getRelease_year());
        dto.setBookPages(reservation.getBook().getPages());
        dto.setBookIsbn(reservation.getBook().getIsbn());
        return dto;
    }
}