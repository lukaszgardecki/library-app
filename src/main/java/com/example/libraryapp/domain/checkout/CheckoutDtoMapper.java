package com.example.libraryapp.domain.checkout;

public class CheckoutDtoMapper {

    public static CheckoutDto map(Checkout checkout) {
        CheckoutDto dto = new CheckoutDto();
        dto.setId(checkout.getId());
        dto.setStartTime(checkout.getStartTime());
        dto.setEndTime(checkout.getEndTime());
        dto.setUserId(checkout.getUser().getId());
        dto.setUserFirstName(checkout.getUser().getFirstName());
        dto.setUserLastName(checkout.getUser().getLastName());
        dto.setUserEmail(checkout.getUser().getEmail());
        dto.setUserCardNumber(checkout.getUser().getCardNumber());
        dto.setBookId(checkout.getBook().getId());
        dto.setBookTitle(checkout.getBook().getTitle());
        dto.setBookAuthor(checkout.getBook().getAuthor());
        dto.setBookPublisher(checkout.getBook().getPublisher());
        dto.setBookReleaseYear(checkout.getBook().getRelease_year());
        dto.setBookPages(checkout.getBook().getPages());
        dto.setBookIsbn(checkout.getBook().getIsbn());
        dto.setIsReturned(checkout.getIsReturned());
        return dto;
    }
}