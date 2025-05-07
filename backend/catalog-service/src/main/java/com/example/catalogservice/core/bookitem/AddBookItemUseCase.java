package com.example.catalogservice.core.bookitem;


import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.dto.BookItemToSaveDto;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemBarcode;
import com.example.catalogservice.domain.model.bookitem.values.BookItemStatus;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
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
