package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.warehouse.model.WarehouseBookItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class BookItemRequestService {
    private final BookFacade bookFacade;
    private final BookItemFacade bookItemFacade;
    private final BookItemRequestFacade bookItemRequestFacade;
    private final UserFacade userFacade;
    private final PersonFacade personFacade;
    private final RackService rackService;

    Page<WarehouseBookItemRequest> getBookRequestList(BookItemRequestStatus status, Pageable pageable) {
        Page<BookItemRequestDto> requests = bookItemRequestFacade.getPageOfBookRequestsByStatus(status, pageable);
        return requests.map(this::getWarehouseBookItemRequest);
    }

    WarehouseBookItemRequest getWarehouseBookItemRequest(BookItemRequestDto request) {
        BookItemDto bookItem = bookItemFacade.getBookItem(new BookItemId(request.getBookItemId()));
        BookDto book = bookFacade.getBook(new BookId(bookItem.getBookId()));
        UserDto user = userFacade.getUserById(new UserId(request.getUserId()));
        PersonDto person = personFacade.getPersonById(new PersonId(user.getPersonId()));
        RackDto rack = RackMapper.toDto(rackService.getRackById(new RackId(bookItem.getRackId())));
        return new WarehouseBookItemRequest(book, bookItem, request, user, person,rack);
    }
}
