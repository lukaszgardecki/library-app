package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookItemUseCase {
    private final BookItemRepository bookItemRepository;

    void execute(Long id) {
        bookItemRepository.deleteById(id);
    }
}
