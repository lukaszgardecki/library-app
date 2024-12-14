package com.example.libraryapp.NEWadapter;

import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWdomain.book.dto.BookDto;
import com.example.libraryapp.NEWdomain.book.dto.BookToSaveDto;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.NEWdomain.user.model.Role.ADMIN;

@RestController
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class BookController {
    private final BookFacade bookFacade;

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookFacade.getBook(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @RoleAuthorization({ADMIN})
    public ResponseEntity<BookDto> addBook(@RequestBody BookToSaveDto book) {
        BookDto savedBook = bookFacade.addBook(book);
        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PatchMapping("/{id}")
    @RoleAuthorization({ADMIN})
    ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookToSaveDto book) {
        BookDto updatedBook = bookFacade.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @RoleAuthorization({ADMIN})
    ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookFacade.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
