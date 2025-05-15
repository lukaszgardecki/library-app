package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.domain.exception.UserHasNotReturnedBooksException;
import com.example.userservice.domain.exception.UserNotFoundException;
import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.person.values.Address;
import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserPerson;
import com.example.userservice.domain.model.user.UserUpdate;
import com.example.userservice.domain.model.user.UserUpdateAdmin;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.BookItemLoanServicePort;
import com.example.userservice.domain.ports.out.FineServicePort;
import com.example.userservice.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class UserService {
    private final BookItemLoanServicePort bookItemLoanService;
    private final UserRepositoryPort userRepository;
    private final FineServicePort fineService;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;

    List<User> getAllByLoansCountDesc(int limit) {
        return userRepository.findAllByLoansCountDesc(limit);
    }

    Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    Page<User> getAllUsersByQuery(String query, Pageable pageable) {
        return userRepository.findAllByQuery(query, pageable);
    }

    User getUserById(UserId id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    User getUserByPersonId(PersonId personId) {
        return userRepository.findByPersonId(personId).orElseThrow(() -> new UserNotFoundException(personId));
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

    User updateUser(UserId userId, UserUpdate userData) {
        User user = getUserById(userId);
        Person person = personFacade.getPersonById(user.getPersonId());
        updateAddress(person, userData);
        updatePersonUser(person, userData);
        userRepository.save(user);
        personFacade.save(person);

        return user;
    }

    User updateUserByAdmin(UserId userId, UserUpdateAdmin userData) {
        User user = getUserById(userId);
        Person person = personFacade.getPersonById(user.getPersonId());
        LibraryCard card = libraryCardFacade.getLibraryCard(user.getCardId());
        updateAddress(person, userData);
        updatePersonAdmin(person, userData);
        updateCard(card, userData);
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

    private void updatePersonUser(Person person, UserPerson userData) {
        Optional.ofNullable(userData.getFirstName()).ifPresent(person::setFirstName);
        Optional.ofNullable(userData.getLastName()).ifPresent(person::setLastName);
        Optional.ofNullable(userData.getPhone()).ifPresent(person::setPhone);
    }

    private void updatePersonAdmin(Person person, UserUpdateAdmin userData) {
        updatePersonUser(person, userData);
        Optional.ofNullable(userData.getGender()).ifPresent(person::setGender);
        Optional.ofNullable(userData.getPesel()).ifPresent(person::setPesel);
        Optional.ofNullable(userData.getDateOfBirth()).ifPresent(person::setDateOfBirth);
        Optional.ofNullable(userData.getNationality()).ifPresent(person::setNationality);
        Optional.ofNullable(userData.getMothersName()).ifPresent(person::setMothersName);
        Optional.ofNullable(userData.getFathersName()).ifPresent(person::setFathersName);
    }

    private void updateAddress(Person person, UserPerson userData) {
        Address address = person.getAddress();
        Optional.ofNullable(userData.getStreetAddress()).ifPresent(address::setStreetAddress);
        Optional.ofNullable(userData.getZipCode()).ifPresent(address::setZipCode);
        Optional.ofNullable(userData.getCity()).ifPresent(address::setCity);
        Optional.ofNullable(userData.getState()).ifPresent(address::setState);
        Optional.ofNullable(userData.getCountry()).ifPresent(address::setCountry);
    }

    private void updateCard(LibraryCard card, UserUpdateAdmin userData) {
        Optional.ofNullable(userData.getCardStatus()).ifPresent(card::setStatus);
    }
}
