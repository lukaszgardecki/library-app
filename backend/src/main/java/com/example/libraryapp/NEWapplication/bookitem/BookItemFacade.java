package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemDto;
import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemToSaveDto;
import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemToUpdateDto;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BookItemFacade {
    private final GetBookItemUseCase getBookItemUseCase;
    private final GetPageOfBookItemsUseCase getPageOfBookItemsUseCase;
    private final GetPageOfBookItemsByBookIdUseCase getPageOfBookItemsByBookIdUseCase;
    private final AddBookItemUseCase addBookItemUseCase;
    private final UpdateBookItemUseCase updateBookItemUseCase;
    private final UpdateBookItemAfterBookRequestUseCase updateBookItemAfterBookRequestUseCase;
    private final UpdateBookItemAfterLoanUseCase updateBookItemAfterLoanUseCase;
    private final UpdateBookItemAfterRenewalUseCase updateBookItemAfterRenewalUseCase;
    private final UpdateBookItemAfterReturnUseCase updateBookItemAfterReturnUseCase;
    private final DeleteBookItemUseCase deleteBookItemUseCase;
    private final VerifyAndGetBookItemForRequestUseCase verifyAndGetBookItemForRequestUseCase;
    private final VerifyAndGetBookItemForLoanUseCase verifyAndGetBookItemForLoanUseCase;

    public BookItemDto getBookItem(Long bookItemId) {
        BookItem bookItem = getBookItemUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }

    public Page<BookItemDto> getPageOfBookItems(Pageable pageable) {
        return getPageOfBookItemsUseCase.execute(pageable).map(BookItemMapper::toDto);
    }

    public Page<BookItemDto> getPageOfBookItemsByBookId(Long bookId, Pageable pageable) {
        return getPageOfBookItemsByBookIdUseCase.execute(bookId, pageable).map(BookItemMapper::toDto);
    }

    public BookItemDto addBookItem(BookItemToSaveDto bookItem) {
        BookItem addedBookItem = addBookItemUseCase.execute(bookItem);
        return BookItemMapper.toDto(addedBookItem);
    }

    public BookItemDto updateBookItem(Long bookItemId, BookItemToUpdateDto bookItem) {
        BookItem updatedBookItem = updateBookItemUseCase.execute(bookItemId, bookItem);
        return BookItemMapper.toDto(updatedBookItem);
    }

    public void updateBookItemAfterRequest(Long bookItemId) {
        updateBookItemAfterBookRequestUseCase.execute(bookItemId);
    }

    public void updateBookItemAfterLoan(Long bookItemId, LocalDateTime borrowedDate, LocalDateTime dueDate) {
        updateBookItemAfterLoanUseCase.execute(bookItemId, borrowedDate, dueDate);
    }

    public void updateBookItemAfterRenewal(Long bookItemId, LocalDateTime dueDate) {
        updateBookItemAfterRenewalUseCase.execute(bookItemId, dueDate);
    }

    public void updateBookItemAfterReturn(Long bookItemId, LocalDateTime returnDate) {
        updateBookItemAfterReturnUseCase.execute(bookItemId, returnDate);
    }

    public void deleteBookItem(Long bookItemId) {
        deleteBookItemUseCase.execute(bookItemId);
    }

    public BookItemDto verifyAndGetBookItemForRequest(Long bookItemId) {
        BookItem bookItem = verifyAndGetBookItemForRequestUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }

    public BookItemDto verifyAndGetBookItemForLoan(Long bookItemId) {
        BookItem bookItem = verifyAndGetBookItemForLoanUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }
}
