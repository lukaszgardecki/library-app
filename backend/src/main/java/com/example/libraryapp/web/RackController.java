package com.example.libraryapp.web;


import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.rack.RackDto;
import com.example.libraryapp.domain.rack.RackService;
import com.example.libraryapp.domain.rack.RackToSaveDto;
import com.example.libraryapp.domain.rack.RackToUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/racks")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RackController {
    private final RackService rackService;

    @GetMapping
    public ResponseEntity<PagedModel<RackDto>> getAllRacks(Pageable pageable) {
        PagedModel<RackDto> collectionModel = rackService.findAllRacks(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RackDto> getRackById(@PathVariable Long id) {
        RackDto rackDto = rackService.findRackById(id);
        return ResponseEntity.ok(rackDto);
    }

    @GetMapping("/{id}/book-items")
    public ResponseEntity<PagedModel<BookItemDto>> getRackBookItemsByRackId(@PathVariable Long id, Pageable pageable) {
        PagedModel<BookItemDto> collectionModel = rackService.findAllRackBookItems(id, pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RackDto> replaceRack(@PathVariable Long id, @RequestBody RackToUpdateDto rack) {
        RackDto replacedRack = rackService.replaceRack(id, rack);
        return ResponseEntity.ok(replacedRack);
    }

    @PatchMapping("/{id}")
    ResponseEntity<RackDto> updateRack(@PathVariable Long id, @RequestBody RackToUpdateDto rack) {
        RackDto updatedRack = rackService.updateRack(id, rack);
        return ResponseEntity.ok(updatedRack);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<RackDto>> searchRackByLocation(@RequestParam(name = "q") String location, Pageable pageable) {
        PagedModel<RackDto> collectionModel = rackService.findRacksByLocation(location, pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RackDto> addRack(@RequestBody RackToSaveDto rackToSave) {
        RackDto savedRack = rackService.addRack(rackToSave);

        URI savedRackUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRack.getId())
                .toUri();
        return ResponseEntity.created(savedRackUri).body(savedRack);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRackById(@PathVariable Long id) {
        rackService.deleteRackById(id);
        return ResponseEntity.noContent().build();
    }
}
