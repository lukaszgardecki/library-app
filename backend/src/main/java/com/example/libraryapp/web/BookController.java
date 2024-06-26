package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.BookService;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.management.LanguageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.libraryapp.domain.member.Role.ADMIN;

@RestController
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<PagedModel<BookDto>> getAllBooks(
            Pageable pageable,
            @RequestParam(value = "lang", required = false)
            List<String> languages
    ) {
        PagedModel<BookDto> collectionModel = bookService.findAllBook(pageable, languages);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.findBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}/book-items")
    public ResponseEntity<List<BookItemDto>> getBookItemsByBookId(@PathVariable Long id) {
        List<BookItemDto> bookItems = bookService.findBookItemsByBookId(id);
        return ResponseEntity.ok(bookItems);
    }

    @GetMapping("/languages/count")
    public ResponseEntity<List<LanguageDto>> getBookLanguages() {
        var languages = bookService.findBookLanguagesWithCount();
        return ResponseEntity.ok(languages);
    }

    @PostMapping
    @RoleAuthorization({ADMIN})
    public ResponseEntity<BookDto> addBook(@RequestBody BookToSaveDto book) {
        BookDto savedBook = bookService.saveBook(book);
        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PutMapping("/{id}")
    @RoleAuthorization({ADMIN})
    ResponseEntity<BookDto> replaceBook(@PathVariable Long id, @RequestBody BookDto book) {
        BookDto replacedBook = bookService.replaceBook(id, book);
        return ResponseEntity.ok(replacedBook);
    }

    @PatchMapping("/{id}")
    @RoleAuthorization({ADMIN})
    ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        BookDto updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @RoleAuthorization({ADMIN})
    ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
