package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.domain.dto.librarycard.LibraryCardDto;
import com.example.userservice.domain.dto.person.PersonDto;
import com.example.userservice.domain.dto.user.*;
import com.example.userservice.domain.exception.UserHasNotReturnedBooksException;
import com.example.userservice.domain.exception.UserNotFoundException;
import com.example.userservice.domain.model.auth.Role;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.person.PersonId;
import com.example.userservice.domain.model.user.*;
import com.example.userservice.domain.ports.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class UserService {
    private final UserRepositoryPort userRepository;
    private final AuthenticationServicePort authenticationService;
    private final CatalogServicePort catalogService;
    private final BookItemLoanServicePort bookItemLoanService;
    private final BookItemRequestServicePort bookItemRequestService;
    private final StatisticsServicePort statisticsService;
    private final FineServicePort fineService;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;

    Page<UserListPreviewProjection> getUserPreviewsByQuery(String query, Pageable pageable) {
        return userRepository.findAllListPreviews(query, pageable);
    }

    Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    List<User> getAllByLoansCountDesc(int limit) {
        return userRepository.findAllByLoansCountDesc(limit);
    }

    User getUserById(UserId id) {
//        authFacade.validateOwnerOrAdminAccess(id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    UserDetails getUserDetails(UserId userId) {
        User user = getUserById(userId);
        return createUserDetails(user);
    }

    UserDetailsAdmin getAdminUserDetailsById(UserId userId) {
        User user = getUserById(userId);
        UserDetailsAdmin userDetails = createAdminUserDetails(user);
        return userDetails;
    }

    UserPreview getUserPreview(UserId userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    PersonDto person = personFacade.getPersonById(user.getPersonId());
                    Role role = authenticationService.getUserAuthByUserId(userId).role();
                    return new UserPreview(
                            user.getId().value(),
                            person.getFirstName(),
                            person.getLastName(),
                            role
                    );
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    void deleteById(UserId userId) {
        PersonId personId = userRepository.findById(userId)
                .map(User::getPersonId)
                .orElse(new PersonId(-1L));
        userRepository.deleteById(userId);
        personFacade.deletePerson(personId);
    }

    void validateUserToDelete(UserId userId) {
        bookItemLoanService.getCurrentLoansByUserId(userId)
                .stream()
                .findAny()
                .ifPresent(loan -> {
                    throw new UserHasNotReturnedBooksException();
                });
        fineService.validateUserForFines(userId);
    }

    User updateUser(UserId userId, UserUpdateDto userData) {
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
        userRepository.save(user);
        personFacade.save(person);

        return user;
    }

    User updateUserByAdmin(UserId userId, UserUpdateAdminDto userData) {
        User user = getUserById(userId);
        PersonDto person = personFacade.getPersonById(user.getPersonId());
        LibraryCardDto card = libraryCardFacade.getLibraryCard(user.getCardId());

        if (userData.getFirstName() != null) person.setFirstName(userData.getFirstName());
        if (userData.getLastName() != null) person.setLastName(userData.getLastName());
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

        if (userData.getCardStatus() != null) card.setStatus(userData.getCardStatus());

        userRepository.save(user);
        personFacade.save(person);
        libraryCardFacade.save(card);
        return user;
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
        UserAuthDto userAuth = authenticationService.getUserAuthByUserId(user.getId());
        return new UserDetails(
                user.getId().value(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getAddress().getStreetAddress(),
                person.getAddress().getZipCode(),
                person.getAddress().getCity(),
                person.getAddress().getState(),
                person.getAddress().getCountry(),
                person.getDateOfBirth(),
                userAuth.username(),
                person.getPhone(),
                person.getPesel(),
                person.getNationality(),
                person.getFathersName(),
                person.getMothersName(),
                card,
                user.getRegistrationDate().value(),
                user.getTotalBooksBorrowed().value(),
                user.getTotalBooksRequested().value(),
                user.getCharge().value(),
                userAuth.status()
        );
    }

    private UserDetailsAdmin createAdminUserDetails(User user) {
        PersonDto person = personFacade.getPersonById(user.getPersonId());
        LibraryCardDto card = libraryCardFacade.getLibraryCard(user.getCardId());
        UserAuthDto userAuth = authenticationService.getUserAuthByUserId(user.getId());
        Map<String, Integer> topGenres = bookItemLoanService.getAllLoansByUserId(user.getId()).stream()
                .collect(Collectors.groupingBy(loan ->
                        catalogService.getBookByBookItemId(new BookItemId(loan.bookItemId())).getSubject(),
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
                ));

        List<Long> loanedItemsIds = bookItemLoanService.getCurrentLoansByUserId(user.getId()).stream()
                .map(BookItemLoanDto::bookItemId)
                .toList();
        List<Long> requestedItemsIds = bookItemRequestService.getUserCurrentBookItemRequests(user.getId()).stream()
                .map(BookItemRequestDto::getBookItemId)
                .toList();
        return new UserDetailsAdmin(
                user.getId().value(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getAddress().getStreetAddress(),
                person.getAddress().getZipCode(),
                person.getAddress().getCity(),
                person.getAddress().getState(),
                person.getAddress().getCountry(),
                person.getDateOfBirth(),
                userAuth.username(),
                person.getPhone(),
                person.getPesel(),
                person.getNationality(),
                person.getFathersName(),
                person.getMothersName(),
                card,
                user.getRegistrationDate().value(),
                user.getTotalBooksBorrowed().value(),
                user.getTotalBooksRequested().value(),
                user.getCharge().value(),
                userAuth.status(),
                loanedItemsIds,
                requestedItemsIds,
                userAuth.role(),
                topGenres,
                statisticsService.countUserLoansPerMonth(user.getId())
        );
    }
}
