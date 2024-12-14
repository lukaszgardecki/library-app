package com.example.libraryapp.NEWapplication.rack;

import com.example.libraryapp.NEWdomain.rack.ports.RackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteRackUseCase {
    private final RackRepository rackRepository;

    void execute(Long id) {
        // TODO: 11.12.2024 tutaj trzeba sprawdzić czy regał nie ma książek...
//        else throw new RackException("Message.RACK_DELETION_FAILED.getMessage(rack.getLocationIdentifier())");


        rackRepository.deleteById(id);
    }
}
