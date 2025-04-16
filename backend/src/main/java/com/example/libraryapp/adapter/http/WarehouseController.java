package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.warehouse.WarehouseFacade;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.dto.ShelfDto;
import com.example.libraryapp.domain.shelf.dto.ShelfToSaveDto;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
@RequestMapping("/api/v1/warehouse")
class WarehouseController {
    private final WarehouseFacade warehouseFacade;
    private final BookItemRequestFacade bookItemRequestFacade;

    @GetMapping("/book-requests/list")
    public ResponseEntity<Page<WarehouseBookItemRequestListViewDto>> getBookItemRequestListView(
            @RequestParam(required = false) BookItemRequestStatus status, Pageable pageable
    ) {
        Page<WarehouseBookItemRequestListViewDto> page = warehouseFacade.getBookItemRequestList(status, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/book-requests/{id}/ready")
    public ResponseEntity<Void> completeRequest(@PathVariable("id") Long requestId) {
        bookItemRequestFacade.changeBookRequestStatusToReady(new RequestId(requestId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/racks")
    public ResponseEntity<?> getAllRacks(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "paged", required = false, defaultValue = "true") boolean paged,
            Pageable pageable
    ) {
        if (paged) {
            Page<RackDto> page = warehouseFacade.getAllRacksPaged(query, pageable);
            return ResponseEntity.ok(page);
        } else {
            List<RackDto> list = warehouseFacade.getAllRacksList(query);
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/shelves")
    public ResponseEntity<?> getAllShelves(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "rack_id", required = false) Long rackId,
            @RequestParam(value = "paged", required = false, defaultValue = "true") boolean paged,
            Pageable pageable
    ) {
        if (paged) {
            Page<ShelfDto> page = warehouseFacade.getAllShelvesPaged(new RackId(rackId), query, pageable);
            return ResponseEntity.ok(page);
        } else {
            List<ShelfDto> list = warehouseFacade.getAllShelvesList(new RackId(rackId), query);
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/racks/{id}")
    public ResponseEntity<RackDto> getRackById(@PathVariable Long id) {
        RackDto rackDto = warehouseFacade.getRack(new RackId(id));
        return ResponseEntity.ok(rackDto);
    }

    @PostMapping("/racks")
    public ResponseEntity<RackDto> addRack(@RequestBody RackToSaveDto rackToSave) {
        RackDto savedRack = warehouseFacade.addRack(rackToSave);

        URI savedRackUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRack.getId())
                .toUri();
        return ResponseEntity.created(savedRackUri).body(savedRack);
    }

    @PostMapping("/shelves")
    public ResponseEntity<ShelfDto> addShelf(@RequestBody ShelfToSaveDto shelfToSave) {
        ShelfDto savedShelf = warehouseFacade.addShelf(shelfToSave);

        URI savedShelfUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedShelf.getId())
                .toUri();
        return ResponseEntity.created(savedShelfUri).body(savedShelf);
    }

    @PatchMapping("/racks/{id}")
    public ResponseEntity<RackDto> updateRack(@RequestBody RackToSaveDto fields, @PathVariable Long id) {
        RackDto updatedRack = warehouseFacade.updateRack(new RackId(id), fields);
        return ResponseEntity.ok(updatedRack);
    }

    @PatchMapping("/shelves/{id}")
    public ResponseEntity<ShelfDto> updateShelf(@RequestBody ShelfToSaveDto fields, @PathVariable Long id) {
        ShelfDto updatedShelf = warehouseFacade.updateShelf(new ShelfId(id), fields);
        return ResponseEntity.ok(updatedShelf);
    }

    @DeleteMapping("/racks/{id}")
    public ResponseEntity<Void> deleteRackById(@PathVariable Long id) {
        warehouseFacade.deleteRack(new RackId(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/shelves/{id}")
    public ResponseEntity<Void> deleteShelfById(@PathVariable Long id) {
        warehouseFacade.deleteShelf(new ShelfId(id));
        return ResponseEntity.noContent().build();
    }
}
