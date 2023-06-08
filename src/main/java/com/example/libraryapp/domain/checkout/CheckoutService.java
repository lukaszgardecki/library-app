package com.example.libraryapp.domain.checkout;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.exception.BookIsAlreadyReturnedException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.UserRepository;
import com.example.libraryapp.domain.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CheckoutService {
    private static final int CHECKOUT_EXP_TIME_IN_DAYS = 30;
    private final CheckoutRepository checkoutRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CheckoutService(CheckoutRepository checkoutRepository,
                           BookRepository bookRepository,
                           UserRepository userRepository,
                           UserService userService) {
        this.checkoutRepository = checkoutRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Optional<List<CheckoutDto>> findAllCheckouts() {
        List<CheckoutDto> list = StreamSupport.stream(checkoutRepository.findAll().spliterator(), false)
                .filter(checkout -> userService.checkIfCurrentLoggedInUserIsAdmin())
                .map(CheckoutDtoMapper::map)
                .toList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    public Optional<List<CheckoutDto>> findCheckoutsByUserId(Long userId) {

        List<CheckoutDto> list = StreamSupport.stream(checkoutRepository.findAll().spliterator(), false)
                .filter(checkout -> Objects.equals(checkout.getUser().getId(), userId))
                .filter(checkout -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(checkout.getUser().getId()))
                .map(CheckoutDtoMapper::map)
                .toList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    public Optional<CheckoutDto> findCheckoutById(Long id) {
        return checkoutRepository.findById(id)
                .filter(checkout -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(checkout.getUser().getId()))
                .map(CheckoutDtoMapper::map);
    }

    @Transactional
    public CheckoutDto borrowABook(CheckoutToSaveDto checkout) {
        if (!bookRepository.existsById(checkout.getBookId())) {
            throw new BookNotFoundException();
        }
        Checkout checkoutToSave = getCheckoutToSave(checkout);
        Checkout savedCheckout = checkoutRepository.save(checkoutToSave);
        return CheckoutDtoMapper.map(savedCheckout);
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
        return CheckoutDtoMapper.map(checkoutToUpdate);
    }

    private Checkout getCheckoutToSave(CheckoutToSaveDto checkout) {
        User user = userRepository.findById(checkout.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(checkout.getBookId())
                .orElseThrow(BookNotFoundException::new);

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
