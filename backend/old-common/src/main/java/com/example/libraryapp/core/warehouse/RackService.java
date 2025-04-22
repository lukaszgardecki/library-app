package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.core.bookitem.BookItemFacade;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import com.example.libraryapp.domain.shelf.exceptions.ShelfException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RackService {
    private final RackRepositoryPort rackRepository;
    private final BookItemFacade bookItemFacade;

    Rack getRackById(RackId id) {
        return rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id));
    }

    Rack save(Rack rack) {
        return rackRepository.save(rack);
    }

    void verifyRackToDelete(RackId rackId) {
        Long bookItemsCount = bookItemFacade.countByParams(rackId, null);
        if (bookItemsCount > 0) throw new ShelfException(MessageKey.RACK_DELETION_FAILED);
    }

    void deleteById(RackId id) {
        rackRepository.deleteById(id);
    }
}
