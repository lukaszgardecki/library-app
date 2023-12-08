package com.example.libraryapp.domain.bookItem;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookItem.mapper.BookItemMapper;
import com.example.libraryapp.domain.config.assembler.BookItemModelAssembler;
import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.bookItem.BookNotFoundException;
import com.example.libraryapp.domain.helper.LibraryGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PagedModel<BookItemDto> findAllBookItems(Pageable pageable) {
        Page<BookItem> bookItemPage =
                pageable.isUnpaged() ? new PageImpl<>(bookItemRepository.findAll()) : bookItemRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(bookItemPage, bookItemModelAssembler);
    }

    public BookItemDto findBookItemById(Long itemId) {
        BookItem bookItem = findBookItem(itemId);
        return bookItemModelAssembler.toModel(bookItem);
    }

    public BookItemDto addBookItem(BookItemToSaveDto item) {
        Book book = findBook(item.getBookId());
        BookItem bookItemToSave = BookItemMapper.map(item);
        bookItemToSave.setStatus(BookItemStatus.AVAILABLE);
        bookItemToSave.setBarcode(LibraryGenerator.generateBarcode(item.getBookId()));
        bookItemToSave.setBook(book);
        BookItem savedBookItem = bookItemRepository.save(bookItemToSave);
        book.addBookItem(bookItemToSave);
        return bookItemModelAssembler.toModel(savedBookItem);
    }

    @Transactional
    public BookItemDto replaceBookItem(Long bookItemId, BookItemDto bookItem) {
        BookItem bookToSave = findBookItem(bookItemId);
        bookToSave.setBarcode(bookItem.getBarcode());
        bookToSave.setIsReferenceOnly(bookItem.getIsReferenceOnly());
        bookToSave.setBorrowed(bookItem.getBorrowed());
        bookToSave.setDueDate(bookItem.getDueDate());
        bookToSave.setPrice(bookItem.getPrice());
        bookToSave.setFormat(bookItem.getFormat());
        bookToSave.setStatus(bookItem.getStatus());
        bookToSave.setDateOfPurchase(bookItem.getDateOfPurchase());
        bookToSave.setPublicationDate(bookItem.getPublicationDate());
        bookToSave.setBook(bookItem.getBook());
        return bookItemModelAssembler.toModel(bookToSave);
    }

    @Transactional
    public BookItemDto updateBookItem(Long bookItemId, BookItemDto bookItem) {
        BookItem bookToUpdate = findBookItem(bookItemId);
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
        return bookItemModelAssembler.toModel(bookToUpdate);
    }

    public void deleteBookItemById(Long bookItemId) {
        BookItem bookItem = findBookItem(bookItemId);
        bookItemRepository.delete(bookItem);
    }

    private BookItem findBookItem(Long id) {
        return bookItemRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
