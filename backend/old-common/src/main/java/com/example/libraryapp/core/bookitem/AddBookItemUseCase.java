package com.example.libraryapp.core.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemBarcode;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.rack.model.RackId;
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
