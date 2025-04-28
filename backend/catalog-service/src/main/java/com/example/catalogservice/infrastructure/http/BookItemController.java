package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.core.bookitem.BookItemFacade;
import com.example.catalogservice.domain.dto.BookItemDto;
import com.example.catalogservice.domain.dto.BookItemToSaveDto;
import com.example.catalogservice.domain.dto.BookItemToUpdateDto;
import com.example.catalogservice.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.RackId;
import com.example.catalogservice.domain.model.bookitem.ShelfId;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/catalog/book-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class BookItemController {
    private final BookItemFacade bookItemFacade;

    @GetMapping
    public ResponseEntity<Page<BookItemWithBookDto>> getPageOfBookItems(
            @RequestParam(value = "book_id", required = false) Long bookId,
            @RequestParam(value = "rack_id", required = false) Long rackId,
            @RequestParam(value = "shelf_id", required = false) Long shelfId,
            @RequestParam(value = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<BookItemWithBookDto> page = bookItemFacade.getPageOfBookItems(
                new BookId(bookId), new RackId(rackId), new ShelfId(shelfId), query, pageable
        );
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookItemDto> getBookItem(@PathVariable Long id) {
        BookItemDto bookItem = bookItemFacade.getBookItem(new BookItemId(id));
        return ResponseEntity.ok(bookItem);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countByParams(
            @RequestParam(value = "rack_id", required = false) Long rackId,
            @RequestParam(value = "shelf_id", required = false) Long shelfId
    ) {
        Long bookItemsCount = bookItemFacade.countByParams(new RackId(rackId), new ShelfId(shelfId));
        return ResponseEntity.ok(bookItemsCount);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookItemDto> addBookItem(@RequestBody BookItemToSaveDto bookItem) {
        BookItemDto savedBook = bookItemFacade.addBookItem(bookItem);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookItemDto> updateBookItem(@PathVariable Long id, @RequestBody BookItemToUpdateDto bookItem) {
        BookItemDto updatedBookItem = bookItemFacade.updateBookItem(new BookItemId(id), bookItem);
        return ResponseEntity.ok(updatedBookItem);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBookItemById(@PathVariable Long id) {
        bookItemFacade.deleteBookItem(new BookItemId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/verify/loan")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForLoan(@PathVariable Long id) {
        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForLoan(new BookItemId(id));
        return ResponseEntity.ok(bookItem);
    }

    @GetMapping("/{id}/verify/request")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForRequest(@PathVariable Long id) {
        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForRequest(new BookItemId(id));
        return ResponseEntity.ok(bookItem);
    }
}
