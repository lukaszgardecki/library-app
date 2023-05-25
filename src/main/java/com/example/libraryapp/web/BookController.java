package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.BookService;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.config.BookModelAssembler;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private final BookService bookService;
    private final BookModelAssembler bookModelAssembler;

    public BookController(BookService bookService, BookModelAssembler bookModelAssembler) {
        this.bookService = bookService;
        this.bookModelAssembler = bookModelAssembler;
    }

    @GetMapping("/books")
    public ResponseEntity<CollectionModel<EntityModel<BookDto>>> getAllBooks() {
        List<BookDto> allBooks = bookService.findAllBooks();

        if (allBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<BookDto>> collectionModel = bookModelAssembler.toCollectionModel(allBooks);
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping("/books")
    public ResponseEntity<EntityModel<BookDto>> addBook(@RequestBody BookToSaveDto book) {
        BookDto savedBook = bookService.saveBook(book);
        EntityModel<BookDto> entityModel = bookModelAssembler.toModel(savedBook);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(entityModel);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> replaceBook(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.replaceBook(id, book)
                .map(b -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/books/{id}")
    ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        try {
            bookService.updateBook(id, book);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> mismatchExceptionHandler() {
        return ResponseEntity.badRequest().body("Id must be a number.");
    }
}
