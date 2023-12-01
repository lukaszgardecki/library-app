package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemService;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/books/{bookId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookItemController {
    private final BookItemService bookItemService;

    @GetMapping
    public ResponseEntity<PagedModel<BookItemDto>> getAllBookItems(@PathVariable Long bookId, Pageable pageable) {
        PagedModel<BookItemDto> collectionModel = bookItemService.findAllBookItems(bookId, pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{bookItemId}")
    public ResponseEntity<BookItemDto> getBookItemByIdAndBookId(
            @PathVariable Long bookId,
            @PathVariable Long bookItemId
    ) {
        return bookItemService.findBookItemByIdAndBookId(bookItemId, bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<BookItemDto> addBookItem(
            @PathVariable Long bookId,
            @RequestBody BookItemDto bookItem
    ) {
        BookItemDto savedBook = bookItemService.addBookItem(bookId, bookItem);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PutMapping("/{bookItemId}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<?> replaceBookItem(
            @PathVariable Long bookItemId,
            @RequestBody BookItemDto bookItem
    ) {
        return bookItemService.replaceBookItem(bookItemId, bookItem)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{bookItemId}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<?> updateBookItem(
            @PathVariable Long bookItemId,
            @RequestBody BookItemDto bookItem
    ) {
        return bookItemService.updateBookItem(bookItemId, bookItem)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{bookItemId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteBookItemById(@PathVariable Long bookItemId) {
        boolean deletionSuccessful = bookItemService.deleteBookItemById(bookItemId);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
