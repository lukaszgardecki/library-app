package com.example.libraryapp.domain.bookItem;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.mapper.BookItemMapper;
import com.example.libraryapp.domain.config.assembler.BookItemModelAssembler;
import com.example.libraryapp.domain.exception.bookItem.BookNotFoundException;
import com.example.libraryapp.domain.helper.LibraryGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookItemService {
    private final BookItemRepository bookItemRepository;
    private final BookRepository bookRepository;
    private final BookItemModelAssembler bookItemModelAssembler;
    private final PagedResourcesAssembler<BookItem> pagedResourcesAssembler;

    public BookItemService(BookItemRepository bookItemRepository,
                           BookRepository bookRepository,
                           BookItemModelAssembler bookItemModelAssembler,
                           PagedResourcesAssembler<BookItem> pagedResourcesAssembler) {
        this.bookItemRepository = bookItemRepository;
        this.bookRepository = bookRepository;
        this.bookItemModelAssembler = bookItemModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public PagedModel<BookItemDto> findAllBookItems(Long bookId, Pageable pageable) {
        Page<BookItem> bookItemPage =
                pageable.isUnpaged() ? new PageImpl<>(bookItemRepository.findByBookId(bookId)) : bookItemRepository.findByBookId(bookId, pageable);
        return pagedResourcesAssembler.toModel(bookItemPage, bookItemModelAssembler);
    }

    public Optional<BookItemDto> findBookItemByIdAndBookId(Long itemId, Long bookId) {
        return bookItemRepository.findByIdAndBookId(itemId, bookId)
                .map(bookItemModelAssembler::toModel);
    }

    public BookItemDto addBookItem(Long bookId, BookItemDto item) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        BookItem bookItemToSave = BookItemMapper.map(item);
        bookItemToSave.setStatus(BookItemStatus.AVAILABLE);
        bookItemToSave.setBarcode(LibraryGenerator.generateBarcode(bookId));
        bookItemToSave.setBook(book);
        BookItem savedBookItem = bookItemRepository.save(bookItemToSave);
        book.addBookItem(bookItemToSave);
        return bookItemModelAssembler.toModel(savedBookItem);
    }

    public Optional<BookItemDto> replaceBookItem(Long bookItemId, BookItemDto bookItem) {
        Optional<BookItem> bookItemToReplace = bookItemRepository.findById(bookItemId);
        if (bookItemToReplace.isPresent()) {
            BookItem bookItemToSave = BookItemMapper.map(bookItem);
            bookItemToSave.setId(bookItemId);
            BookItem savedBookItem = bookItemRepository.save(bookItemToSave);
            return Optional.of(bookItemModelAssembler.toModel(savedBookItem));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<BookItemDto> updateBookItem(Long bookItemId, BookItemDto bookItem) {
        Optional<BookItem> optToUpdate = bookItemRepository.findById(bookItemId);
        if (optToUpdate.isPresent()) {
            BookItem bookToUpdate = optToUpdate.get();
            if (bookItem.getBarcode() != null) bookToUpdate.setBarcode(bookItem.getBarcode());
            if (bookItem.getIsReferenceOnly() != null) bookToUpdate.setIsReferenceOnly(bookItem.getIsReferenceOnly());
            if (bookItem.getBorrowed() != null) bookToUpdate.setBorrowed(bookItem.getBorrowed());
            if (bookItem.getDueDate() != null) bookToUpdate.setDueDate(bookItem.getDueDate());
            if (bookItem.getPrice() != null) bookToUpdate.setPrice(bookItem.getPrice());
            if (bookItem.getFormat() != null) bookToUpdate.setFormat(bookItem.getFormat());
            if (bookItem.getStatus() != null) bookToUpdate.setStatus(bookItem.getStatus());
            if (bookItem.getDateOfPurchase() != null) bookToUpdate.setDateOfPurchase(bookItem.getDateOfPurchase());
            if (bookItem.getPublicationDate() != null) bookToUpdate.setPublicationDate(bookItem.getPublicationDate());
            if (bookItem.getBook() != null) bookToUpdate.setBook(bookItem.getBook());
            return Optional.of(bookItemModelAssembler.toModel(bookToUpdate));
        }
        return Optional.empty();
    }

    public boolean deleteBookItemById(Long bookItemId) {
        Optional<BookItem> optToDelete = bookItemRepository.findById(bookItemId);
        if (optToDelete.isPresent()) {
            bookRepository.deleteById(bookItemId);
            return true;
        }
        return false;
    }
}
