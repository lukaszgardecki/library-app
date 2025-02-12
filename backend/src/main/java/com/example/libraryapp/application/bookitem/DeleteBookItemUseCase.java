package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookItemUseCase {
    private final BookItemRepository bookItemRepository;

    void execute(Long id) {
        bookItemRepository.deleteById(id);
    }
}
