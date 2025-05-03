package com.example.warehouseservice.infrastructure.http;

import com.example.warehouseservice.core.shelf.ShelfFacade;
import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.dto.ShelfDto;
import com.example.warehouseservice.domain.dto.ShelfToSaveDto;
import com.example.warehouseservice.domain.model.shelf.ShelfId;
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
@RequestMapping("/warehouse/shelves")
class ShelfController {
    private final ShelfFacade shelfFacade;

    @GetMapping
    public ResponseEntity<?> getAllShelves(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "rack_id", required = false) Long rackId,
            @RequestParam(name = "paged", required = false, defaultValue = "true") boolean paged,
            Pageable pageable
    ) {
        if (paged) {
            Page<ShelfDto> page = shelfFacade.getAllShelvesPaged(new RackId(rackId), query, pageable);
            return ResponseEntity.ok(page);
        } else {
            List<ShelfDto> list = shelfFacade.getAllShelvesList(new RackId(rackId), query);
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShelfDto> getShelfById(@PathVariable Long id) {
        ShelfDto shelfDto = shelfFacade.getShelfById(new ShelfId(id));
        return ResponseEntity.ok(shelfDto);
    }

    @PostMapping
    public ResponseEntity<ShelfDto> addShelf(@RequestBody ShelfToSaveDto shelfToSave) {
        ShelfDto savedShelf = shelfFacade.addShelf(shelfToSave);

        URI savedShelfUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedShelf.getId())
                .toUri();
        return ResponseEntity.created(savedShelfUri).body(savedShelf);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShelfDto> updateShelf(@RequestBody ShelfToSaveDto fields, @PathVariable Long id) {
        ShelfDto updatedShelf = shelfFacade.updateShelf(new ShelfId(id), fields);
        return ResponseEntity.ok(updatedShelf);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelfById(@PathVariable Long id) {
        shelfFacade.deleteShelf(new ShelfId(id));
        return ResponseEntity.noContent().build();
    }
}
