package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.infrastructure.http.dto.BookDto;
import com.example.catalogservice.infrastructure.http.dto.BookToSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/catalog/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class BookController {
    private final BookFacade bookFacade;

    @GetMapping("/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = BookMapper.toDto(bookFacade.getBook(new BookId(id)));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookDto> addBook(@RequestBody BookToSaveDto book) {
        BookDto savedBook = BookMapper.toDto(bookFacade.addBook(BookMapper.toModel(book)));
        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookToSaveDto book) {
        BookDto updatedBook = BookMapper.toDto(bookFacade.updateBook(new BookId(id), BookMapper.toModel(book)));
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookFacade.deleteBook(new BookId(id));
        return ResponseEntity.noContent().build();
    }
}
