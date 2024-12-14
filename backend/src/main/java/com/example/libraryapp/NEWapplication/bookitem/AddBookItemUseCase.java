package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemToSaveDto;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemToSaveDto bookItem) {
        BookItem bookItemToSave = prepareBookItem(bookItem);
        return bookItemService.saveBookItem(bookItemToSave);
    }

    private BookItem prepareBookItem(BookItemToSaveDto bookItem) {
        BookItem model = bookItem.toModel();
        model.setStatus(BookItemStatus.AVAILABLE);
        model.setBarcode(bookItemService.generateBarcode(bookItem.getBookId()));
        model.setBookId(bookItem.getBookId());
        model.setRackId(bookItem.getRackId());
        return model;
    }
}
