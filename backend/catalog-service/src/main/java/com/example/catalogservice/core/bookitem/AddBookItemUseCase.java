package com.example.catalogservice.core.bookitem;


import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemBarcode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItem bookItem) {
        BookItem savedBookItem = bookItemService.save(bookItem);
        BookItemBarcode barcode = bookItemService.generateNewBookItemBarcode(savedBookItem.getId());
        bookItemService.updateBookItemBarcode(savedBookItem.getId(), barcode);
        return bookItemService.getBookItemById(savedBookItem.getId());
    }


}
