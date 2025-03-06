package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.rack.RackFacade;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/racks")
@PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
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
