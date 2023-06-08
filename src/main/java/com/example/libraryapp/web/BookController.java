package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.BookService;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.config.assembler.BookModelAssembler;
import com.example.libraryapp.domain.exception.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<EntityModel<BookDto>> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .map(bookModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BookIsNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Reason", e.getMessage())
                    .build();
        }
    }

    @PutMapping("/books")
    ResponseEntity<?> replaceBook(@RequestBody BookDto book) {
        return bookService.replaceBook(book)
                .map(bookModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/books/{id}")
    ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        try {
            BookDto updatedBook = bookService.updateBook(id, book);
            EntityModel<BookDto> entityModel = bookModelAssembler.toModel(updatedBook);
            return ResponseEntity.ok(entityModel);
        } catch (BookNotFoundException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> mismatchExceptionHandler() {
        return ResponseEntity.badRequest().body("Id must be a number.");
    }
}
