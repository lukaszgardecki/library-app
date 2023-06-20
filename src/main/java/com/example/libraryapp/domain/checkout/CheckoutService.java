package com.example.libraryapp.domain.checkout;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.config.assembler.CheckoutModelAssembler;
import com.example.libraryapp.domain.exception.*;
import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.UserRepository;
import com.example.libraryapp.domain.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {
    private static final int CHECKOUT_EXP_TIME_IN_DAYS = 30;
    private final CheckoutRepository checkoutRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CheckoutModelAssembler checkoutModelAssembler;
    private final PagedResourcesAssembler<Checkout> pagedResourcesAssembler;

    public CheckoutService(CheckoutRepository checkoutRepository,
                           BookRepository bookRepository,
                           UserRepository userRepository,
                           UserService userService,
                           CheckoutModelAssembler checkoutModelAssembler,
                           PagedResourcesAssembler<Checkout> pagedResourcesAssembler) {
        this.checkoutRepository = checkoutRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.checkoutModelAssembler = checkoutModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public PagedModel<CheckoutDto> findAllCheckouts(Pageable pageable) {
        boolean currentLoggedInUserIsAdmin = userService.checkIfCurrentLoggedInUserIsAdmin();
        if (currentLoggedInUserIsAdmin) {

            Page<Checkout> checkoutDtoPage =
                    pageable.isUnpaged() ? new PageImpl<>(checkoutRepository.findAll()) : checkoutRepository.findAll(pageable);
            return pagedResourcesAssembler.toModel(checkoutDtoPage, checkoutModelAssembler);
        }
        throw new CheckoutNotFoundException();
    }

    public PagedModel<CheckoutDto> findCheckoutsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(userId);

        if (userIsAdminOrDataOwner) {
            List<Checkout> checkoutList = checkoutRepository.findAll()
                    .stream()
                    .filter(checkout -> checkout.getUser().getId().equals(userId))
                    .toList();
            Page<Checkout> checkoutDtoPage;
            if (pageable.isUnpaged()) {
                checkoutDtoPage = new PageImpl<>(checkoutList);
            } else {
                checkoutDtoPage = new PageImpl<>(checkoutList, pageable, checkoutList.size());
            }
            return pagedResourcesAssembler.toModel(checkoutDtoPage, checkoutModelAssembler);
        }
        throw new CheckoutNotFoundException();
    }

    public Optional<CheckoutDto> findCheckoutById(Long id) {
        return checkoutRepository.findById(id)
                .filter(checkout -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(checkout.getUser().getId()))
                .map(checkoutModelAssembler::toModel);
    }

    @Transactional
    public CheckoutDto borrowABook(CheckoutToSaveDto checkout) {
        User user = userRepository.findById(checkout.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(checkout.getBookId())
                .orElseThrow(BookNotFoundException::new);

        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(checkout.getUserId());

        if (userIsAdminOrDataOwner) {
            Checkout checkoutToSave = getCheckoutToSave(user, book);
            Checkout savedCheckout = checkoutRepository.save(checkoutToSave);
            return checkoutModelAssembler.toModel(savedCheckout);
        } else throw new CheckoutCannotBeCreatedException();
    }

    @Transactional
    public CheckoutDto returnABook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        book.setAvailability(Boolean.TRUE);

        Checkout checkoutToUpdate = checkoutRepository.findCheckoutsByBook_Id(bookId).stream()
                .filter(b -> b.getIsReturned().equals(Boolean.FALSE))
                .findFirst()
                .orElseThrow(BookIsAlreadyReturnedException::new);
        checkoutToUpdate.setIsReturned(Boolean.TRUE);
        return checkoutModelAssembler.toModel(checkoutToUpdate);
    }

    private Checkout getCheckoutToSave(User user, Book book) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(CHECKOUT_EXP_TIME_IN_DAYS);

        Checkout checkoutToSave = new Checkout();
        checkoutToSave.setUser(user);
        checkoutToSave.setBook(book);
        checkoutToSave.setStartTime(startTime);
        checkoutToSave.setEndTime(endTime);
        checkoutToSave.setIsReturned(Boolean.FALSE);
        return  checkoutToSave;
    }
}
