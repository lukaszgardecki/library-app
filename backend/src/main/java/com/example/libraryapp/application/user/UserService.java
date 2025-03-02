package com.example.libraryapp.application.user;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.fine.FineFacade;
import com.example.libraryapp.application.librarycard.LibraryCardFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.statistics.StatisticsFacade;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.user.dto.UserUpdateAdminDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.example.libraryapp.domain.user.exceptions.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.domain.user.model.*;
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class UserService {
    private final UserRepositoryPort userRepository;
    private final UserCredentialsService credentialsService;
    private final AuthenticationFacade authFacade;
    private final BookFacade bookFacade;
    private final BookItemFacade bookItemFacade;
    private final BookItemLoanFacade bookItemLoanFacade;
    private final BookItemRequestFacade bookItemRequestFacade;
    private final FineFacade fineFacade;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;
    private final StatisticsFacade statisticsFacade;

    Page<UserListPreviewProjection> getUserPreviewsByQuery(String query, Pageable pageable) {
        return userRepository.findAllListPreviews(query, pageable);
    }

    Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    List<User> getAllByLoansCountDesc(int limit) {
        return userRepository.findAllByLoansCountDesc(limit);
    }

    User getUserById(Long id) {
//        authFacade.validateOwnerOrAdminAccess(id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    UserDetails getUserDetails(Long userId) {
        User user = getUserById(userId);
        return createUserDetails(user);
    }

    UserDetailsAdmin getAdminUserDetailsById(Long userId) {
        User user = getUserById(userId);
        UserDetailsAdmin userDetails = createAdminUserDetails(user);
        return userDetails;
    }

    UserPreview getUserPreview(Long userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        return userRepository.findById(userId)
                .map(user -> {
                    PersonDto person = personFacade.getPersonById(user.getPersonId());
                    return new UserPreview(
                            user.getId(),
                            person.getFirstName(),
                            person.getLastName(),
                            user.getRole()
                    );
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    void deleteById(Long userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        Long personId = userRepository.findById(userId)
                .map(User::getPersonId)
                .orElse(-1L);
        userRepository.deleteById(userId);
        personFacade.deletePerson(personId);
    }

    void validateUserToDelete(Long userId) {
        bookItemLoanFacade.getCurrentLoansByUserId(userId)
                .stream()
                .findAny()
                .ifPresent(loan -> {
                    throw new UserHasNotReturnedBooksException();
                });
        fineFacade.validateUserForFines(userId);
    }

    User updateUser(Long userId, UserUpdateDto userData) {
        authFacade.validateOwnerOrAdminAccess(userId);
        User user = getUserById(userId);
        PersonDto person = personFacade.getPersonById(user.getPersonId());

        if (userData.getStreetAddress() != null) person.getAddress().setStreetAddress(userData.getStreetAddress());
        if (userData.getZipCode() != null) person.getAddress().setZipCode(userData.getZipCode());
        if (userData.getCity() != null) person.getAddress().setCity(userData.getCity());
        if (userData.getState() != null) person.getAddress().setState(userData.getState());
        if (userData.getCountry() != null) person.getAddress().setCountry(userData.getCountry());

        if (userData.getFirstName() != null) person.setFirstName(userData.getFirstName());
        if (userData.getLastName() != null) person.setLastName(userData.getLastName());
        if (userData.getPhone() != null) person.setPhone(userData.getPhone());
        if (userData.getEmail() != null && !user.getEmail().equals(userData.getEmail())) {
            credentialsService.validateEmail(userData.getEmail());
            user.setEmail(userData.getEmail());
        }
        if (userData.getPassword() != null) {
            user.setPassword(credentialsService.encodePassword(userData.getPassword()));
        }
        userRepository.save(user);
        personFacade.save(person);

        return user;
    }

    User updateUserByAdmin(Long userId, UserUpdateAdminDto userData) {
        authFacade.validateOwnerOrAdminAccess(userId);
        User user = getUserById(userId);
        PersonDto person = personFacade.getPersonById(user.getPersonId());
        LibraryCardDto card = libraryCardFacade.getLibraryCard(user.getCardId());

        if (userData.getFirstName() != null) person.setFirstName(userData.getFirstName());
        if (userData.getLastName() != null) person.setLastName(userData.getLastName());
        if (userData.getEmail() != null && !user.getEmail().equals(userData.getEmail())) {
            credentialsService.validateEmail(userData.getEmail());
            user.setEmail(userData.getEmail());
        }
        if (userData.getStreetAddress() != null) person.getAddress().setStreetAddress(userData.getStreetAddress());
        if (userData.getZipCode() != null) person.getAddress().setZipCode(userData.getZipCode());
        if (userData.getCity() != null) person.getAddress().setCity(userData.getCity());
        if (userData.getState() != null) person.getAddress().setState(userData.getState());
        if (userData.getCountry() != null) person.getAddress().setCountry(userData.getCountry());

        if (userData.getPhone() != null) person.setPhone(userData.getPhone());

        if (userData.getGender() != null) person.setGender(userData.getGender());
        if (userData.getPesel() != null) person.setPesel(userData.getPesel());
        if (userData.getDateOfBirth() != null) person.setDateOfBirth(userData.getDateOfBirth());
        if (userData.getNationality() != null) person.setNationality(userData.getNationality());
        if (userData.getMothersName() != null) person.setMothersName(userData.getMothersName());
        if (userData.getFathersName() != null) person.setFathersName(userData.getFathersName());

        if (userData.getAccountStatus() != null) user.setStatus(userData.getAccountStatus());
        if (userData.getCardStatus() != null) card.setStatus(userData.getCardStatus());
        if (userData.getRole() != null) user.setRole(userData.getRole());

        userRepository.save(user);
        personFacade.save(person);
        libraryCardFacade.save(card);
        return user;
    }

    void updateUserOnRequest(Long userId) {
        userRepository.incrementTotalBooksRequested(userId);
    }

    void updateUserOnRequestCancellation(Long userId) {
        userRepository.decrementTotalBooksRequested(userId);
    }

    void updateUserOnReturn(Long userId) {
        userRepository.decrementTotalBooksBorrowed(userId);
    }

    void updateUserOnLoss(Long userId) {
        userRepository.decrementTotalBooksBorrowed(userId);
    }

    void updateUserOnRenewal(Long userId) {
        // nothing ?
    }

    void updateUserOnLoan(Long userId) {
        userRepository.decrementTotalBooksRequested(userId);
        userRepository.incrementTotalBooksBorrowed(userId);
    }

    void updateUserOnFinePaid(Long userId, BigDecimal fineAmount) {
        userRepository.reduceChargeByAmount(userId, fineAmount);
    }

    long countAll() {
        return userRepository.count();
    }

    long countNewRegisteredUsersByMonth(int month, int year) {
        return userRepository.countNewRegisteredUsersByMonth(month, year);
    }

    private UserDetails createUserDetails(User user) {
        PersonDto person = personFacade.getPersonById(user.getPersonId());
        LibraryCardDto card = libraryCardFacade.getLibraryCard(user.getCardId());
        return new UserDetails(
                user.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getAddress().getStreetAddress(),
                person.getAddress().getZipCode(),
                person.getAddress().getCity(),
                person.getAddress().getState(),
                person.getAddress().getCountry(),
                person.getDateOfBirth(),
                user.getEmail(),
                person.getPhone(),
                person.getPesel(),
                person.getNationality(),
                person.getFathersName(),
                person.getMothersName(),
                card,
                user.getRegistrationDate(),
                user.getTotalBooksBorrowed(),
                user.getTotalBooksRequested(),
                user.getCharge(),
                user.getStatus()
        );
    }

    private UserDetailsAdmin createAdminUserDetails(User user) {
        PersonDto person = personFacade.getPersonById(user.getPersonId());
        LibraryCardDto card = libraryCardFacade.getLibraryCard(user.getCardId());
        Map<String, Integer> topGenres = bookItemLoanFacade.getAllLoansByUserId(user.getId()).stream()
                .collect(Collectors.groupingBy(
                        loan -> {
                            BookItemDto bookItem = bookItemFacade.getBookItem(loan.bookItemId());
                            return bookFacade.getBook(bookItem.getBookId()).getSubject();
                        },
                        Collectors.summingInt(lending -> 1)
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));;

        List<Long> loanedItemsIds = bookItemLoanFacade.getCurrentLoansByUserId(user.getId()).stream()
                .map(BookItemLoanDto::bookItemId)
                .toList();
        List<Long> requestedItemsIds = bookItemRequestFacade.getUserCurrentBookItemRequests(user.getId()).stream()
                .map(BookItemRequestDto::bookItemId)
                .toList();
        return new UserDetailsAdmin(
                user.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getAddress().getStreetAddress(),
                person.getAddress().getZipCode(),
                person.getAddress().getCity(),
                person.getAddress().getState(),
                person.getAddress().getCountry(),
                person.getDateOfBirth(),
                user.getEmail(),
                person.getPhone(),
                person.getPesel(),
                person.getNationality(),
                person.getFathersName(),
                person.getMothersName(),
                card,
                user.getRegistrationDate(),
                user.getTotalBooksBorrowed(),
                user.getTotalBooksRequested(),
                user.getCharge(),
                user.getStatus(),
                loanedItemsIds,
                requestedItemsIds,
                user.getRole(),
                topGenres,
                statisticsFacade.countUserLoansPerMonth(user.getId())
        );
    }
}
