package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.BookService;
import com.example.libraryapp.domain.book.dto.BookDto;
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
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<PagedModel<BookDto>> getAllBooks(Pageable pageable) {
        PagedModel<BookDto> collectionModel = bookService.findAllBook(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto book) {
        BookDto savedBook = bookService.saveBook(book);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<BookDto> replaceBook(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.replaceBook(id, book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deletionSuccessful = bookService.deleteBook(id);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
