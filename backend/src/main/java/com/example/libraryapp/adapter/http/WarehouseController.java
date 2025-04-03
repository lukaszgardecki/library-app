package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.warehouse.WarehouseFacade;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.dto.ShelfDto;
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
    public ResponseEntity<Page<RackDto>> getAllRacks(
            @RequestParam(value = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<RackDto> page = warehouseFacade.getAllRacks(query, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/shelves")
    public ResponseEntity<Page<ShelfDto>> getAllShelves(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "rack_id", required = false) Long rackId,
            Pageable pageable
    ) {
        Page<ShelfDto> page = warehouseFacade.getAllShelves(new RackId(rackId), query, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
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

    @DeleteMapping("/racks/{id}")
    public ResponseEntity<?> deleteRackById(@PathVariable Long id) {
        warehouseFacade.deleteRack(new RackId(id));
        return ResponseEntity.noContent().build();
    }
}
