package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.core.bookitem.BookItemFacade;
import com.example.catalogservice.domain.dto.BookDto;
import com.example.catalogservice.domain.dto.BookItemDto;
import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/catalog/book-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class CatalogController {
    private final BookFacade bookFacade;
    private final BookItemFacade bookItemFacade;

    @GetMapping("/{bookItemId}/book")
    ResponseEntity<BookDto> getBookByBookItemId(@PathVariable Long bookItemId) {
        BookItemDto bookItem = bookItemFacade.getBookItem(new BookItemId(bookItemId));
        BookDto book = bookFacade.getBook(new BookId(bookItem.getBookId()));
        return ResponseEntity.ok(book);
    }
}
