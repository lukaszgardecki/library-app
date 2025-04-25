package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.domain.dto.BookDto;
import com.example.catalogservice.domain.model.LoanReturnDate;
import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.domain.dto.RackDto;
import com.example.catalogservice.domain.dto.ShelfDto;
import com.example.catalogservice.domain.exception.BookItemNotFoundException;
import com.example.catalogservice.domain.model.bookitem.*;
import com.example.catalogservice.domain.ports.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.BookItemRequestServicePort;
import com.example.catalogservice.domain.ports.EventListenerPort;
import com.example.catalogservice.domain.ports.WarehouseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class BookItemService implements EventListenerPort {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemRequestServicePort bookItemRequestService;
    private final WarehouseServicePort warehouseService;
    private final BookItemBarcodeGenerator generator;
    private final BookFacade bookFacade;

    Page<BookItemWithBookDto> getAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, String query, Pageable pageable) {
        Page<BookItem> bookItems = bookItemRepository.findAllByParams(bookId, rackId, shelfId, query, pageable);
        List<BookId> ids = bookItems.map(BookItem::getBookId).toList();
        List<BookDto> books = bookFacade.getBooksByIds(ids);
        Map<Long, BookDto> bookMap = books.stream()
                .collect(Collectors.toMap(BookDto::getId, Function.identity()));
        return bookItems.map(bookItem -> {
            BookDto bookDto = bookMap.get(bookItem.getBookId().value());
            RackDto rack = warehouseService.getRackById(bookItem.getRackId());
            ShelfDto shelf = warehouseService.getShelfById(bookItem.getShelfId());
            return BookItemMapper.toDto(bookItem, bookDto, rack, shelf);
        });
    }

    BookItem getBookItemById(BookItemId id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    Title getBookTitleByBookId(BookId bookId) {
        return new Title(bookFacade.getBook(bookId).getTitle());
    }

    BookItem save(BookItem bookItem) {
        return bookItemRepository.save(bookItem);
    }

    BookItemBarcode generateNewBookItemBarcode(BookItemId bookItemId) {
        return generator.generateBarcode(bookItemId);
    }

    void updateBookItemBarcode(BookItemId bookItemId, BookItemBarcode barcode) {
        bookItemRepository.updateBarcode(bookItemId, barcode);
    }

    @Override
    public void updateBookItemOnRequest(BookItemId bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            bookItemRepository.updateStatus(bookItemId, BookItemStatus.REQUESTED);
        }
    }

    @Override
    public void updateBookItemOnRequestCancellation(BookItemId bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        boolean bookIsRequested = bookItemRequestService.isBookItemRequested(bookItemId);
        boolean hasStatusRequested = bookItem.getStatus() == BookItemStatus.REQUESTED;
        if (!bookIsRequested && hasStatusRequested) {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }
    }

    @Override
    public void updateBookItemOnReturn(BookItemId bookItemId, LoanReturnDate dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        boolean bookIsReserved = bookItemRequestService.isBookItemRequested(bookItemId);

        if (bookIsReserved) {
            bookItem.setStatus(BookItemStatus.REQUESTED);
        } else {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }

        bookItem.setDueDate(new LoanDueDate(dueDate.value()));
        save(bookItem);
    }

    @Override
    public void updateBookItemOnLoss(BookItemId bookItemId) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOST);
        bookItem.setDueDate(null);
        save(bookItem);
    }

    @Override
    public void updateBookItemOnRenewal(BookItemId bookItemId, LoanDueDate dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setDueDate(dueDate);
        save(bookItem);
    }

    @Override
    public void updateBookItemOnLoan(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate) {
        BookItem bookItem = getBookItemById(bookItemId);
        bookItem.setStatus(BookItemStatus.LOANED);
        bookItem.setBorrowedDate(creationDate);
        bookItem.setDueDate(dueDate);
        save(bookItem);
    }
}
