package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.book.values.BookId;
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

    public BookItem getBookItem(BookItemId bookItemId) {
        return getBookItemUseCase.execute(bookItemId);
    }

    public Page<BookItem> getPageOfBookItems(
            @Nullable BookId bookId,
            @Nullable RackId rackId,
            @Nullable ShelfId shelfId,
            @Nullable String query,
            Pageable pageable
    ) {
        return getPageOfBookItemsUseCase.execute(bookId, rackId, shelfId, query, pageable);
    }

    public BookItem addBookItem(BookItem bookItem) {
        return addBookItemUseCase.execute(bookItem);
    }

    public BookItem updateBookItem(BookItemId bookItemId, BookItem bookItem) {
        return updateBookItemUseCase.execute(bookItemId, bookItem);
    }

    public void deleteBookItem(BookItemId bookItemId) {
        deleteBookItemUseCase.execute(bookItemId);
    }

    public Long countByParams(@Nullable RackId rackId,@Nullable ShelfId shelfId) {
        return countByParamsUseCase.execute(rackId, shelfId);
    }

    public BookItem verifyAndGetBookItemForRequest(BookItemId bookItemId) {
        return verifyAndGetBookItemForRequestUseCase.execute(bookItemId);
    }

    public BookItem verifyAndGetBookItemForLoan(BookItemId bookItemId) {
        return verifyAndGetBookItemForLoanUseCase.execute(bookItemId);
    }
}
