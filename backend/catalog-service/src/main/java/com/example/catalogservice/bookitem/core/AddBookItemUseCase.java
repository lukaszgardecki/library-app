package com.example.catalogservice.bookitem.core;


import com.example.catalogservice.book.domain.model.BookId;
import com.example.catalogservice.bookitem.domain.dto.BookItemToSaveDto;
import com.example.catalogservice.bookitem.domain.model.BookItem;
import com.example.catalogservice.bookitem.domain.model.BookItemBarcode;
import com.example.catalogservice.bookitem.domain.model.BookItemStatus;
import com.example.catalogservice.bookitem.domain.model.RackId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemToSaveDto bookItem) {
        BookItem bookItemToSave = prepareBookItem(bookItem);
        BookItem savedBookItem = bookItemService.save(bookItemToSave);
        BookItemBarcode barcode = bookItemService.generateNewBookItemBarcode(savedBookItem.getId());
        bookItemService.updateBookItemBarcode(savedBookItem.getId(), barcode);
        return bookItemService.getBookItemById(savedBookItem.getId());
    }

    private BookItem prepareBookItem(BookItemToSaveDto bookItem) {
        BookItem model = bookItem.toModel();
        model.setStatus(BookItemStatus.AVAILABLE);
        model.setBookId(new BookId(bookItem.getBookId()));
        model.setRackId(new RackId(bookItem.getRackId()));
        return model;
    }
}
