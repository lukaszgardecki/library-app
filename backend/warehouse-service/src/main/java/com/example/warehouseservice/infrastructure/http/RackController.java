package com.example.warehouseservice.infrastructure.http;

import com.example.warehouseservice.core.rack.RackFacade;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.infrastructure.http.dto.RackDto;
import com.example.warehouseservice.infrastructure.http.dto.RackToSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
@RequestMapping("/warehouse/racks")
class RackController {
    private final RackFacade rackFacade;

    @GetMapping
    ResponseEntity<?> getAllRacks(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "paged", required = false, defaultValue = "true") boolean paged,
            Pageable pageable
    ) {
        if (paged) {
            Page<RackDto> page = rackFacade.getAllRacksPaged(query, pageable).map(RackMapper::toDto);
            return ResponseEntity.ok(page);
        } else {
            List<RackDto> list = rackFacade.getAllRacksList(query).stream().map(RackMapper::toDto).toList();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<RackDto> getRackById(@PathVariable Long id) {
        RackDto rackDto = RackMapper.toDto(rackFacade.getRackById(new RackId(id)));
        return ResponseEntity.ok(rackDto);
    }

    @PostMapping
    ResponseEntity<RackDto> addRack(@RequestBody RackToSaveDto rackToSave) {
        RackDto savedRack = RackMapper.toDto(rackFacade.addRack(RackMapper.toModel(rackToSave)));

        URI savedRackUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRack.getId())
                .toUri();
        return ResponseEntity.created(savedRackUri).body(savedRack);
    }

    @PatchMapping("/{id}")
    ResponseEntity<RackDto> updateRack(@RequestBody RackToSaveDto fields, @PathVariable Long id) {
        RackDto updatedRack = RackMapper.toDto(rackFacade.updateRack(new RackId(id), RackMapper.toModel(fields)));
        return ResponseEntity.ok(updatedRack);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRackById(@PathVariable Long id) {
        rackFacade.deleteRack(new RackId(id));
        return ResponseEntity.noContent().build();
    }
}
