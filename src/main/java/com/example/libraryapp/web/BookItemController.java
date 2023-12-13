package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemService;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToSaveDto;
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
@RequestMapping(value = "/api/v1/book-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookItemController {
    private final BookItemService bookItemService;

    @GetMapping
    public ResponseEntity<PagedModel<BookItemDto>> getAllBookItems(Pageable pageable) {
        PagedModel<BookItemDto> collectionModel = bookItemService.findAllBookItems(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookItemDto> getBookItemById(@PathVariable Long id) {
        BookItemDto bookItem = bookItemService.findBookItemById(id);
        return ResponseEntity.ok(bookItem);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<BookItemDto> addBookItem(@RequestBody BookItemToSaveDto bookItem) {
        BookItemDto savedBook = bookItemService.addBookItem(bookItem);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<BookItemDto> replaceBookItem(@PathVariable Long id, @RequestBody BookItemToSaveDto bookItem) {
        BookItemDto replacedBookItem = bookItemService.replaceBookItem(id, bookItem);
        return ResponseEntity.ok(replacedBookItem);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<BookItemDto> updateBookItem(@PathVariable Long id, @RequestBody BookItemDto bookItem) {
        BookItemDto updatedBookItem = bookItemService.updateBookItem(id, bookItem);
        return ResponseEntity.ok(updatedBookItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteBookItemById(@PathVariable Long id) {
        bookItemService.deleteBookItemById(id);
        return ResponseEntity.noContent().build();
    }
}
