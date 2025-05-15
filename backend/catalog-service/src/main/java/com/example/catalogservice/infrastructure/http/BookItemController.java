package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.core.bookitem.BookItemFacade;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.infrastructure.http.dto.BookItemDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemToSaveDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemToUpdateDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemWithBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/catalog/book-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class BookItemController {
    private final BookItemFacade bookItemFacade;
    private final DetailsAggregator detailsAggregator;

    @GetMapping
    ResponseEntity<Page<BookItemWithBookDto>> getPageOfBookItems(
            @RequestParam(name = "book_id", required = false) Long bookId,
            @RequestParam(name = "rack_id", required = false) Long rackId,
            @RequestParam(name = "shelf_id", required = false) Long shelfId,
            @RequestParam(name = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<BookItem> page = bookItemFacade.getPageOfBookItems(
                new BookId(bookId), new RackId(rackId), new ShelfId(shelfId), query, pageable
        );
        Page<BookItemWithBookDto> bookItemWithDetails = detailsAggregator.getBookItemWithDetails(page);
        return new ResponseEntity<>(bookItemWithDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookItemDto> getBookItem(@PathVariable Long id) {
        BookItemDto bookItem = BookItemMapper.toDto(bookItemFacade.getBookItem(new BookItemId(id)));
        return ResponseEntity.ok(bookItem);
    }

    @GetMapping("/count")
    ResponseEntity<Long> countByParams(
            @RequestParam(name = "rack_id", required = false) Long rackId,
            @RequestParam(name = "shelf_id", required = false) Long shelfId
    ) {
        Long bookItemsCount = bookItemFacade.countByParams(new RackId(rackId), new ShelfId(shelfId));
        return ResponseEntity.ok(bookItemsCount);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookItemDto> addBookItem(@RequestBody BookItemToSaveDto bookItemToSave) {
        BookItem model = BookItemMapper.toModel(bookItemToSave);
        BookItemDto savedBook = BookItemMapper.toDto(bookItemFacade.addBookItem(model));

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookItemDto> updateBookItem(@PathVariable Long id, @RequestBody BookItemToUpdateDto bookItem) {
        BookItem model = BookItemMapper.toModel(bookItem);
        BookItemDto updatedBookItem = BookItemMapper.toDto(
                bookItemFacade.updateBookItem(new BookItemId(id), model)
        );
        return ResponseEntity.ok(updatedBookItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteBookItemById(@PathVariable Long id) {
        bookItemFacade.deleteBookItem(new BookItemId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/verify/loan")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForLoan(@PathVariable Long id) {
        BookItemDto bookItem = BookItemMapper.toDto(bookItemFacade.verifyAndGetBookItemForLoan(new BookItemId(id)));
        return ResponseEntity.ok(bookItem);
    }

    @GetMapping("/{id}/verify/request")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForRequest(@PathVariable Long id) {
        BookItemDto bookItem = BookItemMapper.toDto(bookItemFacade.verifyAndGetBookItemForRequest(new BookItemId(id)));
        return ResponseEntity.ok(bookItem);
    }
}
