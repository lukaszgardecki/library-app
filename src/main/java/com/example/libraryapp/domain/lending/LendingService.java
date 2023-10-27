package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.config.assembler.LendingModelAssembler;
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
public class LendingService {
    private static final int CHECKOUT_EXP_TIME_IN_DAYS = 30;
    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final LendingModelAssembler lendingModelAssembler;
    private final PagedResourcesAssembler<Lending> pagedResourcesAssembler;

    public LendingService(LendingRepository lendingRepository,
                          BookRepository bookRepository,
                          UserRepository userRepository,
                          UserService userService,
                          LendingModelAssembler lendingModelAssembler,
                          PagedResourcesAssembler<Lending> pagedResourcesAssembler) {
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.lendingModelAssembler = lendingModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public PagedModel<LendingDto> findAllCheckouts(Pageable pageable) {
        boolean currentLoggedInUserIsAdmin = userService.checkIfCurrentLoggedInUserIsAdmin();
        if (currentLoggedInUserIsAdmin) {

            Page<Lending> checkoutDtoPage =
                    pageable.isUnpaged() ? new PageImpl<>(lendingRepository.findAll()) : lendingRepository.findAll(pageable);
            return pagedResourcesAssembler.toModel(checkoutDtoPage, lendingModelAssembler);
        }
        throw new LendingNotFoundException();
    }

    public PagedModel<LendingDto> findCheckoutsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(userId);

        if (userIsAdminOrDataOwner) {
            List<Lending> lendingList = lendingRepository.findAll()
                    .stream()
                    .filter(checkout -> checkout.getUser().getId().equals(userId))
                    .toList();
            Page<Lending> checkoutDtoPage;
            if (pageable.isUnpaged()) {
                checkoutDtoPage = new PageImpl<>(lendingList);
            } else {
                checkoutDtoPage = new PageImpl<>(lendingList, pageable, lendingList.size());
            }
            return pagedResourcesAssembler.toModel(checkoutDtoPage, lendingModelAssembler);
        }
        throw new LendingNotFoundException();
    }

    public Optional<LendingDto> findCheckoutById(Long id) {
        return lendingRepository.findById(id)
                .filter(checkout -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(checkout.getUser().getId()))
                .map(lendingModelAssembler::toModel);
    }

    @Transactional
    public LendingDto borrowABook(LendingToSaveDto checkout) {
        User user = userRepository.findById(checkout.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(checkout.getBookId())
                .orElseThrow(BookNotFoundException::new);

        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(checkout.getUserId());

        if (userIsAdminOrDataOwner) {
            Lending lendingToSave = getCheckoutToSave(user, book);
            Lending savedLending = lendingRepository.save(lendingToSave);
            return lendingModelAssembler.toModel(savedLending);
        } else throw new LendingCannotBeCreatedException();
    }

    @Transactional
    public LendingDto returnABook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        book.setAvailability(Boolean.TRUE);

        Lending lendingToUpdate = lendingRepository.findCheckoutsByBook_Id(bookId).stream()
                .filter(b -> b.getIsReturned().equals(Boolean.FALSE))
                .findFirst()
                .orElseThrow(BookIsAlreadyReturnedException::new);
        lendingToUpdate.setIsReturned(Boolean.TRUE);
        return lendingModelAssembler.toModel(lendingToUpdate);
    }

    private Lending getCheckoutToSave(User user, Book book) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(CHECKOUT_EXP_TIME_IN_DAYS);

        Lending lendingToSave = new Lending();
        lendingToSave.setUser(user);
        lendingToSave.setBook(book);
        lendingToSave.setStartTime(startTime);
        lendingToSave.setEndTime(endTime);
        lendingToSave.setIsReturned(Boolean.FALSE);
        return lendingToSave;
    }
}
