package com.example.libraryapp.adapter;

import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookitem.dto.BookItemToUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/book-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class BookItemController {
    private final BookItemFacade bookItemFacade;

    @GetMapping
    public ResponseEntity<Page<BookItemDto>> getPageOfBookItems(
            @RequestParam(value = "bookId", required = false) Long bookId, Pageable pageable
    ) {
        Page<BookItemDto> collectionModel;
        if (bookId != null) {
            collectionModel = bookItemFacade.getPageOfBookItemsByBookId(bookId, pageable);
        } else {
            collectionModel = bookItemFacade.getPageOfBookItems(pageable);
        }
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookItemDto> getBookItem(@PathVariable Long id) {
        BookItemDto bookItem = bookItemFacade.getBookItem(id);
        return ResponseEntity.ok(bookItem);
    }

    @PostMapping
    public ResponseEntity<BookItemDto> addBookItem(@RequestBody BookItemToSaveDto bookItem) {
        BookItemDto savedBook = bookItemFacade.addBookItem(bookItem);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PatchMapping("/{id}")
    ResponseEntity<BookItemDto> updateBookItem(@PathVariable Long id, @RequestBody BookItemToUpdateDto bookItem) {
        BookItemDto updatedBookItem = bookItemFacade.updateBookItem(id, bookItem);
        return ResponseEntity.ok(updatedBookItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookItemById(@PathVariable Long id) {
        bookItemFacade.deleteBookItem(id);
        return ResponseEntity.noContent().build();
    }
}
