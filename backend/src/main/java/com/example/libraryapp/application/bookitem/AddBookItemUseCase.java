package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.bookitem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemToSaveDto bookItem) {
        BookItem bookItemToSave = prepareBookItem(bookItem);
        return bookItemService.save(bookItemToSave);
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
