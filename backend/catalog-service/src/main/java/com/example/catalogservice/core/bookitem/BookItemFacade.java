package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.dto.BookItemDto;
import com.example.catalogservice.domain.dto.BookItemToSaveDto;
import com.example.catalogservice.domain.dto.BookItemToUpdateDto;
import com.example.catalogservice.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookItemFacade {
    private final GetBookItemUseCase getBookItemUseCase;
    private final GetPageOfBookItemsUseCase getPageOfBookItemsUseCase;
    private final AddBookItemUseCase addBookItemUseCase;
    private final UpdateBookItemUseCase updateBookItemUseCase;
    private final DeleteBookItemUseCase deleteBookItemUseCase;
    private final CountByParamsUseCase countByParamsUseCase;
    private final VerifyAndGetBookItemForRequestUseCase verifyAndGetBookItemForRequestUseCase;
    private final VerifyAndGetBookItemForLoanUseCase verifyAndGetBookItemForLoanUseCase;

    public BookItemDto getBookItem(BookItemId bookItemId) {
        BookItem bookItem = getBookItemUseCase.execute(bookItemId);
        return BookItemMapper.toDto(bookItem);
    }

    public Page<BookItemWithBookDto> getPageOfBookItems(
            @Nullable BookId bookId,
            @Nullable RackId rackId,
            @Nullable ShelfId shelfId,
            @Nullable String query,
            Pageable pageable
    ) {
        return getPageOfBookItemsUseCase.execute(bookId, rackId, shelfId, query, pageable);
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

    public Long countByParams(@Nullable RackId rackId,@Nullable ShelfId shelfId) {
        return countByParamsUseCase.execute(rackId, shelfId);
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
