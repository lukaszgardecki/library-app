package com.example.libraryapp.application.rack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteRackUseCase {
    private final RackService rackService;

    void execute(Long id) {
        rackService.verifyRackToDelete(id);
        rackService.deleteById(id);
    }
}
