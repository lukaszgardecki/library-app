package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookitem.dto.BookItemToUpdateDto;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.rack.model.RackId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookItemFacade {
    private final GetBookItemUseCase getBookItemUseCase;
    private final GetPageOfBookItemsUseCase getPageOfBookItemsUseCase;
    private final GetPageOfBookItemsByBookIdUseCase getPageOfBookItemsByBookIdUseCase;
    private final GetPageOfBookItemsByRackIdUseCase getPageOfBookItemsByRackIdUseCase;
    private final AddBookItemUseCase addBookItemUseCase;
    private final UpdateBookItemUseCase updateBookItemUseCase;
    private final DeleteBookItemUseCase deleteBookItemUseCase;
    private final VerifyAndGetBookItemForRequestUseCase verifyAndGetBookItemForRequestUseCase;
    private final VerifyAndGetBookItemForLoanUseCase verifyAndGetBookItemForLoanUseCase;

    public BookItemDto getBookItem(BookItemId bookItemId) {
        BookItem bookItem = getBookItemUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }

    public Page<BookItemDto> getPageOfBookItems(Pageable pageable) {
        return getPageOfBookItemsUseCase.execute(pageable).map(BookItemMapper::toDto);
    }

    public Page<BookItemDto> getPageOfBookItemsByBookId(BookId bookId, Pageable pageable) {
        return getPageOfBookItemsByBookIdUseCase.execute(bookId, pageable).map(BookItemMapper::toDto);
    }

    public Page<BookItemDto> getPageOfBookItemsByRackId(RackId rackId, Pageable pageable) {
        return getPageOfBookItemsByRackIdUseCase.execute(rackId, pageable).map(BookItemMapper::toDto);
    }

    public BookItemDto addBookItem(BookItemToSaveDto bookItem) {
        BookItem addedBookItem = addBookItemUseCase.execute(bookItem);
        return BookItemMapper.toDto(addedBookItem);
    }

    public BookItemDto updateBookItem(BookItemId bookItemId, BookItemToUpdateDto bookItem) {
        BookItem updatedBookItem = updateBookItemUseCase.execute(bookItemId, bookItem);
        return BookItemMapper.toDto(updatedBookItem);
    }

    public void deleteBookItem(BookItemId bookItemId) {
        deleteBookItemUseCase.execute(bookItemId);
    }

    public BookItemDto verifyAndGetBookItemForRequest(BookItemId bookItemId) {
        BookItem bookItem = verifyAndGetBookItemForRequestUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }

    public BookItemDto verifyAndGetBookItemForLoan(BookItemId bookItemId) {
        BookItem bookItem = verifyAndGetBookItemForLoanUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }
}
