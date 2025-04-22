package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.rack.model.RackId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteRackUseCase {
    private final RackService rackService;

    void execute(RackId id) {
        rackService.verifyRackToDelete(id);
        rackService.deleteById(id);
    }
}
