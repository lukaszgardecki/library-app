package com.example.userservice.infrastructure.http.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.core.user.UserFacade;
import com.example.userservice.domain.integration.loan.BookItemLoan;
import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.BookItemLoanServicePort;
import com.example.userservice.domain.ports.out.BookItemRequestServicePort;
import com.example.userservice.domain.ports.out.CatalogServicePort;
import com.example.userservice.domain.ports.out.StatisticsServicePort;
import com.example.userservice.infrastructure.http.librarycard.LibraryCardMapper;
import com.example.userservice.infrastructure.http.librarycard.dto.LibraryCardDto;
import com.example.userservice.infrastructure.http.user.dto.UserDetailsAdminDto;
import com.example.userservice.infrastructure.http.user.dto.UserDetailsDto;
import com.example.userservice.infrastructure.http.user.dto.UserListPreviewDto;
import com.example.userservice.infrastructure.http.user.dto.UserPreviewDto;
import com.example.userservice.infrastructure.integration.authservice.AuthServiceAdapter;
import com.example.userservice.infrastructure.integration.authservice.dto.AuthDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DetailsAggregator {
    private final BookItemLoanServicePort bookItemLoanService;
    private final BookItemRequestServicePort bookItemRequestService;
    private final CatalogServicePort catalogService;
    private final StatisticsServicePort statisticsService;
    private final AuthServiceAdapter authService;
    private final UserFacade userFacade;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;

    Page<UserListPreviewDto> getUserList(String query, Pageable pageable) {
        Set<PersonId> personIds = new HashSet<>();

        Page<User> users = userFacade.getAllUsersByQuery(query, pageable);
        Page<Person> persons = personFacade.getAllByQuery(query, pageable);

        personIds.addAll(users.map(User::getPersonId).toList());
        personIds.addAll(persons.map(Person::getId).toList());

        List<UserListPreviewDto> allPreviews = personIds.stream()
                .map(personId -> {
                    User user = userFacade.getUserByPersonId(personId);
                    Person person = personFacade.getPersonById(personId);
                    AuthDetailsDto auth = authService.getUserAuthByUserId(user.getId());
                    return new UserListPreviewDto(
                            user.getId().value(),
                            person.getFirstName().value(),
                            person.getLastName().value(),
                            auth.username(),
                            user.getRegistrationDate().value(),
                            auth.status()
                    );
                })
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allPreviews.size());
        List<UserListPreviewDto> pageContent = allPreviews.subList(start, end);

        return new PageImpl<>(pageContent, pageable, allPreviews.size());
    }

    UserPreviewDto getUserPreview(UserId id) {
        User user = userFacade.getUserById(id);
        Person person = personFacade.getPersonById(user.getPersonId());
        AuthDetailsDto auth = authService.getUserAuthByUserId(user.getId());
        return new UserPreviewDto(
                user.getId().value(),
                person.getFirstName().value(),
                person.getLastName().value(),
                auth.role()
        );
    }

    UserDetailsAdminDto getUserDetailsAdmin(UserId id) {
        User user = userFacade.getUserById(id);
        Person person = personFacade.getPersonById(user.getPersonId());
        AuthDetailsDto auth = authService.getUserAuthByUserId(user.getId());
        LibraryCardDto card = LibraryCardMapper.toDto(libraryCardFacade.getLibraryCard(user.getCardId()));
        List<BookItemLoan> allUserLoans = bookItemLoanService.getAllLoansByUserId(user.getId());

        Map<String, Integer> topGenres = allUserLoans.stream()
                .collect(Collectors.groupingBy(loan ->
                                catalogService.getBookByBookItemId(loan.getBookItemId()).getSubject().value(),
                        Collectors.summingInt(loan -> 1)
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
                .map(loan -> loan.getBookItemId().value())
                .toList();
        List<Long> requestedItemsIds = bookItemRequestService.getUserCurrentBookItemRequests(user.getId()).stream()
                .map(request -> request.getBookItemId().value())
                .toList();

        Map<Integer, Integer> loansPerMonth = statisticsService.countUserLoansPerMonth(user.getId());
        return new UserDetailsAdminDto(
                user.getId().value(),
                person.getFirstName().value(),
                person.getLastName().value(),
                person.getGender().name(),
                person.getAddress().getStreetAddress().value(),
                person.getAddress().getZipCode().value(),
                person.getAddress().getCity().value(),
                person.getAddress().getState().value(),
                person.getAddress().getCountry().value(),
                person.getDateOfBirth().value(),
                auth.username(),
                person.getPhone().value(),
                person.getPesel().value(),
                person.getNationality().value(),
                person.getFathersName().value(),
                person.getMothersName().value(),
                card,
                user.getRegistrationDate().value(),
                user.getTotalBooksBorrowed().value(),
                user.getTotalBooksRequested().value(),
                user.getCharge().value(),
                auth.status(),
                loanedItemsIds,
                requestedItemsIds,
                auth.role(),
                topGenres,
                loansPerMonth
        );
    }

    UserDetailsDto getUserDetails(UserId id) {
        User user = userFacade.getUserById(id);
        Person person = personFacade.getPersonById(user.getPersonId());
        AuthDetailsDto auth = authService.getUserAuthByUserId(user.getId());
        LibraryCardDto card = LibraryCardMapper.toDto(libraryCardFacade.getLibraryCard(user.getCardId()));
        return new UserDetailsDto(
                user.getId().value(),
                person.getFirstName().value(),
                person.getLastName().value(),
                person.getGender().name(),
                person.getAddress().getStreetAddress().value(),
                person.getAddress().getZipCode().value(),
                person.getAddress().getCity().value(),
                person.getAddress().getState().value(),
                person.getAddress().getCountry().value(),
                person.getDateOfBirth().value(),
                auth.username(),
                person.getPhone().value(),
                person.getPesel().value(),
                person.getNationality().value(),
                person.getFathersName().value(),
                person.getMothersName().value(),
                card,
                user.getRegistrationDate().value(),
                user.getTotalBooksBorrowed().value(),
                user.getTotalBooksRequested().value(),
                user.getCharge().value(),
                auth.status()
        );
    }
}
