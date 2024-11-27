package com.example.libraryapp.domain.bookItem;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToUpdateDto;
import com.example.libraryapp.domain.bookItem.mapper.BookItemMapper;
import com.example.libraryapp.domain.bookItem.assembler.BookItemModelAssembler;
import com.example.libraryapp.domain.exception.book.BookNotFoundException;
import com.example.libraryapp.domain.exception.bookItem.BookItemException;
import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.rack.RackNotFoundException;
import com.example.libraryapp.domain.helper.LibraryGenerator;
import com.example.libraryapp.domain.rack.Rack;
import com.example.libraryapp.domain.rack.RackRepository;
import com.example.libraryapp.management.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookItemService {
    private final BookItemRepository bookItemRepository;
    private final BookRepository bookRepository;
    private final RackRepository rackRepository;
    private final BookItemModelAssembler bookItemModelAssembler;
    private final PagedResourcesAssembler<BookItem> pagedResourcesAssembler;

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
        Rack rack = findRack(item.getRackId());
        BookItem bookItemToSave = BookItemMapper.map(item);
        bookItemToSave.setStatus(BookItemStatus.AVAILABLE);
        bookItemToSave.setBarcode(LibraryGenerator.generateBarcode(item.getBookId()));
        bookItemToSave.setBook(book);
        bookItemToSave.setRack(rack);
        BookItem savedBookItem = bookItemRepository.save(bookItemToSave);
        book.addBookItem(bookItemToSave);
        rack.addBookItem(bookItemToSave);
        return bookItemModelAssembler.toModel(savedBookItem);
    }

    @Transactional
    public BookItemDto replaceBookItem(Long bookItemId, BookItemToUpdateDto bookItem) {
        Book book = findBook(bookItem.getBookId());
        Rack rack = findRack(bookItem.getRackId());
        BookItem bookToSave = findBookItem(bookItemId);
//        bookToSave.setBarcode(bookItem.getBarcode());
        bookToSave.setIsReferenceOnly(bookItem.getIsReferenceOnly());
        bookToSave.setBorrowed(bookItem.getBorrowed());
        bookToSave.setDueDate(bookItem.getDueDate());
        bookToSave.setPrice(bookItem.getPrice());
        bookToSave.setFormat(bookItem.getFormat());
        bookToSave.setStatus(bookItem.getStatus());
        bookToSave.setDateOfPurchase(bookItem.getDateOfPurchase());
        bookToSave.setPublicationDate(bookItem.getPublicationDate());
        bookToSave.setBook(book);
        bookToSave.setRack(rack);
        return bookItemModelAssembler.toModel(bookToSave);
    }

    @Transactional
    public BookItemDto updateBookItem(Long bookItemId, BookItemToUpdateDto bookItem) {
        BookItem bookToUpdate = findBookItem(bookItemId);
        if (bookItem.getIsReferenceOnly() != null) bookToUpdate.setIsReferenceOnly(bookItem.getIsReferenceOnly());
        if (bookItem.getBorrowed() != null) bookToUpdate.setBorrowed(bookItem.getBorrowed());
        if (bookItem.getDueDate() != null) bookToUpdate.setDueDate(bookItem.getDueDate());
        if (bookItem.getPrice() != null) bookToUpdate.setPrice(bookItem.getPrice());
        if (bookItem.getFormat() != null) bookToUpdate.setFormat(bookItem.getFormat());
        if (bookItem.getStatus() != null) bookToUpdate.setStatus(bookItem.getStatus());
        if (bookItem.getDateOfPurchase() != null) bookToUpdate.setDateOfPurchase(bookItem.getDateOfPurchase());
        if (bookItem.getPublicationDate() != null) bookToUpdate.setPublicationDate(bookItem.getPublicationDate());
        if (bookItem.getBookId() != null) {
            Book book = findBook(bookItem.getBookId());
            bookToUpdate.setBook(book);
        }
        if (bookItem.getRackId() != null) {
            Rack rack = findRack(bookItem.getRackId());
            bookToUpdate.setRack(rack);
        }
        return bookItemModelAssembler.toModel(bookToUpdate);
    }

    public void deleteBookItemById(Long bookItemId) {
        BookItem bookItem = findBookItem(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.LOANED || bookItem.getStatus() == BookItemStatus.RESERVED) {
            throw new BookItemException(Message.BOOK_ITEM_DELETION_FAILED.getMessage());
        }
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

    private Rack findRack(Long id) {
        return rackRepository.findById(id)
                .orElseThrow(() -> new RackNotFoundException(id));
    }
}
