package com.example.libraryapp.application.rack;

import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.rack.exceptions.RackException;
import com.example.libraryapp.domain.rack.ports.RackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RackService {
    private final RackRepository rackRepository;
    private final BookItemFacade bookItemFacade;

    void verifyRackToDelete(Long id) {
        bookItemFacade.getPageOfBookItemsByRackId(id, null)
                .stream()
                .findAny()
                .ifPresent(bookItem -> {
                    throw new RackException(MessageKey.RACK_DELETION_FAILED);
                });
    }

    void deleteById(Long id) {
        rackRepository.deleteById(id);
    }
}
