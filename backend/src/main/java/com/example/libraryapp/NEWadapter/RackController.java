package com.example.libraryapp.NEWadapter;


import com.example.libraryapp.NEWapplication.rack.RackFacade;
import com.example.libraryapp.NEWdomain.rack.dto.RackDto;
import com.example.libraryapp.NEWdomain.rack.dto.RackToSaveDto;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.NEWdomain.user.model.Role.ADMIN;

@RestController
@RequestMapping("/api/v1/racks")
@RoleAuthorization({ADMIN})
@RequiredArgsConstructor
class RackController {
    private final RackFacade rackFacade;

    @GetMapping("/{id}")
    public ResponseEntity<RackDto> getRackById(@PathVariable Long id) {
        RackDto rackDto = rackFacade.getRack(id);
        return ResponseEntity.ok(rackDto);
    }

    @PostMapping
    public ResponseEntity<RackDto> addRack(@RequestBody RackToSaveDto rackToSave) {
        RackDto savedRack = rackFacade.addRack(rackToSave);

        URI savedRackUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRack.getId())
                .toUri();
        return ResponseEntity.created(savedRackUri).body(savedRack);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRackById(@PathVariable Long id) {
        rackFacade.deleteRack(id);
        return ResponseEntity.noContent().build();
    }
}
