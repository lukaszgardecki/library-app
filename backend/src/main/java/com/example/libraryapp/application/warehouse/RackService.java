package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.rack.exceptions.RackException;
import com.example.libraryapp.domain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RackService {
    private final RackRepositoryPort rackRepository;
    private final BookItemFacade bookItemFacade;

    Rack getRackById(RackId id) {
        return rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id));
    }

    void verifyRackToDelete(RackId rackId) {
        bookItemFacade.getPageOfBookItems(null, rackId, null, null)
                .stream()
                .findAny()
                .ifPresent(bookItem -> {
                    throw new RackException(MessageKey.RACK_DELETION_FAILED);
                });
    }

    void deleteById(RackId id) {
        rackRepository.deleteById(id);
    }
}
