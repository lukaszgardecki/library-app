package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.core.bookitem.BookItemFacade;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.infrastructure.http.dto.BookDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/catalog/book-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class CatalogController {
    private final BookFacade bookFacade;
    private final BookItemFacade bookItemFacade;

    @GetMapping("/{bookItemId}/book")
    ResponseEntity<BookDto> getBookByBookItemId(@PathVariable Long bookItemId) {
        BookItemDto bookItem = BookItemMapper.toDto(bookItemFacade.getBookItem(new BookItemId(bookItemId)));
        BookDto book = BookMapper.toDto(bookFacade.getBook(new BookId(bookItem.getBookId())));
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{bookItemId}/book/id")
    ResponseEntity<Long> getBookIdByBookItemId(@PathVariable Long bookItemId) {
        BookItemDto bookItem = BookItemMapper.toDto(bookItemFacade.getBookItem(new BookItemId(bookItemId)));
        BookDto book = BookMapper.toDto(bookFacade.getBook(new BookId(bookItem.getBookId())));
        return ResponseEntity.ok(book.getId());
    }
}
