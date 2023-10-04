package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.BookService;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
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
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<PagedModel<BookDto>> getAllBooks(Pageable pageable) {
        PagedModel<BookDto> collectionModel = bookService.findAllBooks(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PostMapping("/books")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<BookDto> addBook(@RequestBody BookToSaveDto book) {
        BookDto savedBook = bookService.saveBook(book);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<?> replaceBook(@RequestBody BookDto book) {
        return bookService.replaceBook(book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/books/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        BookDto updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }
}
