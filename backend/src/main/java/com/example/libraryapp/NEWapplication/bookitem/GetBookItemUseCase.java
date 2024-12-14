package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemUseCase {
    private final BookItemRepository bookItemRepository;

    BookItem execute(Long id) {
        return bookItemRepository.findById(id).orElseThrow(() -> new BookItemNotFoundException(id));
    }
}
