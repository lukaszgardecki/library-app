package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.mapper.BookMapper;
import com.example.libraryapp.domain.bookItem.BookItemFormat;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.lending.LendingStatus;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.ActionRequest;
import com.example.libraryapp.management.Constants;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance( TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class LendingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;
    private HttpHeaders adminHeader;
    private HttpHeaders userHeader;
    private HttpHeaders user5Header;

    @BeforeAll
    void authenticate() {
        LoginRequest admin = new LoginRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");
        this.adminHeader = authenticate(admin);

        LoginRequest user = new LoginRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");
        this.userHeader = authenticate(user);

        LoginRequest user5 = new LoginRequest();
        user5.setUsername("p.smerf@gmail.com");
        user5.setPassword("userpass3");
        this.user5Header = authenticate(user5);
    }

    @Test
    @Order(1)
    void shouldReturnAllLendingsIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int allLendingsLength = documentContext.read("$._embedded.lendingDtoList.length()");
        assertThat(allLendingsLength).isEqualTo(6);

        getResponse = restTemplate
                .exchange("/api/v1/lendings?memberId=1", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(getResponse.getBody());
        int memberLendingsLength = documentContext.read("$._embedded.lendingDtoList.length()");
        assertThat(memberLendingsLength).isEqualTo(3);
    }

    @Test
    @Order(2)
    void shouldReturnPageOf3LendingsIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/lendings?page=1&size=3", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.lendingDtoList.length()");
        assertThat(bookListLength).isEqualTo(3);
        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(3);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(6);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(2);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(1);
    }

    @Test
    @Order(3)
    void shouldReturnAllUsersLendingsIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = new HttpEntity<>(user5Header);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?memberId=5", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int memberLendingsLength = documentContext.read("$._embedded.lendingDtoList.length()");
        assertThat(memberLendingsLength).isEqualTo(1);
    }

    @Test
    @Order(4)
    void shouldNotReturnAllUsersLendingsIfUserIdDoesNotExist() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?memberId=99999999", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        getResponse = restTemplate
                .exchange("/api/v1/lendings?memberId=badrequest", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @Order(5)
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersLendingsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?memberId=" + memberId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(6)
    void shouldNotReturnAllUsersLendingsIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(7)
    void shouldNotReturnAllCheckoutsIfUserIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/lendings", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        getResponse = restTemplate
                .getForEntity("/api/v1/lendings?memberId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(8)
    @CsvSource({
            "1, 1, 1",
            "3, 1, 3",
            "6, 6, 6"
    })
    void shouldReturnAnExistingLendingIfAdminRequested(Long lendingId, Long memberId, Long bookItemId) {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/" + lendingId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        LendingDto returnedLending = getLendingFromResponse(getResponse);
        MemberDto user = findMemberById(memberId, request);
        BookItemDto book = findBookItemById(bookItemId);

        assertThat(returnedLending.getId()).isNotNull();
        assertThat(returnedLending.getCreationDate()).isNotNull();
        assertThat(returnedLending.getDueDate()).isEqualTo(returnedLending.getCreationDate().plusDays(Constants.MAX_LENDING_DAYS));
        assertThat(returnedLending.getStatus()).isNotNull();
        assertThat(returnedLending.getMember()).isEqualTo(user);
        assertThat(returnedLending.getBookItem()).isEqualTo(book);
    }

    @Test
    @Order(9)
    void shouldReturnAnExistingLendingIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> adminRequest = new HttpEntity<>(adminHeader);
        HttpEntity<Object> userRequest = new HttpEntity<>(user5Header);
        MemberDto member = findMemberById(5L, adminRequest);
        BookItemDto book = findBookItemById(2L);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/2", HttpMethod.GET, userRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        LendingDto returnedLending = getLendingFromResponse(getResponse);
        assertThat(returnedLending.getId()).isEqualTo(2L);
        assertThat(returnedLending.getCreationDate()).isEqualTo(LocalDate.parse("2023-05-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(returnedLending.getDueDate()).isEqualTo(LocalDate.parse("2123-06-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(returnedLending.getReturnDate()).isNull();
        assertThat(returnedLending.getStatus()).isEqualTo(LendingStatus.CURRENT);
        assertThat(returnedLending.getMember()).isEqualTo(member);
        assertThat(returnedLending.getBookItem()).isEqualTo(book);
    }

    @ParameterizedTest
    @Order(10)
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingLendingIfUserRequestedAndDoesNotOwnThisData(Long lendingId) {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/" + lendingId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(11)
    @CsvSource({
            "7", "10", "99999"
    })
    void shouldNotReturnLendingThatDoesNotExist(Long lendingId) {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/" + lendingId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @Order(12)
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingLendingIfUserIsNotAuthenticated(Long lendingId) {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/lendings/" + lendingId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(32)
    void shouldBorrowABookIfAdminRequestedAndMemberHasReservedABookEarlier() {
        ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
        HttpEntity<Object> adminRequest = new HttpEntity<>(lendingToSave, adminHeader);
        MemberDto memberBefore = findMemberById(2L, adminRequest);
        BookItemDto bookItemBefore = findBookItemById(1L);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", adminRequest, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newlyCreatedLendingLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getLendingResponse = restTemplate
                .exchange(newlyCreatedLendingLocation, HttpMethod.GET, adminRequest, String.class);
        assertThat(getLendingResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getReservationResponse = restTemplate
                .exchange("/api/v1/reservations/9", HttpMethod.GET, adminRequest, String.class);
        ReservationResponse reservation = getReservationFromResponse(getReservationResponse);
        assertThat(getReservationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);

        MemberDto memberAfter = findMemberById(2L, adminRequest);
        assertThat(memberAfter.getTotalBooksBorrowed()).isEqualTo(memberBefore.getTotalBooksBorrowed() + 1);
        assertThat(memberAfter.getTotalBooksReserved()).isEqualTo(memberBefore.getTotalBooksReserved() - 1);

        LendingDto returnedLending = getLendingFromResponse(getLendingResponse);
        assertThat(returnedLending.getId()).isNotNull();
        assertThat(returnedLending.getCreationDate()).isNotNull();
        assertThat(returnedLending.getDueDate()).isEqualTo(returnedLending.getCreationDate().plusDays(Constants.MAX_LENDING_DAYS));
        assertThat(returnedLending.getReturnDate()).isNull();
        assertThat(returnedLending.getStatus()).isEqualTo(LendingStatus.CURRENT);
        assertThat(returnedLending.getMember()).isEqualTo(memberBefore);
        assertThat(returnedLending.getBookItem()).isEqualTo(bookItemBefore);

        BookItemDto bookItemAfter = findBookItemById(1L);
        assertThat(bookItemAfter.getBorrowed()).isEqualTo(returnedLending.getCreationDate());
        assertThat(bookItemAfter.getDueDate()).isEqualTo(returnedLending.getDueDate());
        assertThat(bookItemAfter.getStatus()).isEqualTo(BookItemStatus.LOANED);
    }

    @ParameterizedTest
    @Order(13)
    @CsvSource({
            "1, 540200000002",
            "1, 540200000100",
            "2, 540200000005",
            "3, 540200000004",
            "3, 540200000005",
            "3, 540200000007",
            "4, 540200000001",
            "4, 540200000008",
            "5, 540200000004",
            "5, 540200000007",
            "6, 540200000005",
            "6, 540200000001",
            "7, 540200000006",
            "7, 540200000004"
    })
    void shouldNotBorrowABookIfAdminRequestedButUserHasNotReservedABookEarlier(Long memberId, String bookBarcode) {
        ActionRequest checkoutToSave = createRequestBody(memberId, bookBarcode);
        HttpEntity<?> request = new HttpEntity<>(checkoutToSave, adminHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @Order(14)
    @CsvSource({
            "1, 540200000002",
            "1, 540200000003",
            "4, 540200000004"
    })
    void shouldNotBorrowABookIfAdminRequestedAndUserHasNotReturnedOneYet(Long memberId, String bookBarcode) {
        ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
        HttpEntity<?> request = new HttpEntity<>(lendingToSave, adminHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(15)
    void shouldNotBorrowABookIfAdminRequestedAndBookIdDoesNotExist() {
        ActionRequest lendingToSave = createRequestBody(2L, "540200099999");
        HttpEntity<?> request = new HttpEntity<>(lendingToSave, adminHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(16)
    void shouldNotBorrowABookIfAdminRequestedAndUserIdDoesNotExist() {
        ActionRequest checkoutToSave = createRequestBody(99999999L, "540200000002");
        HttpEntity<?> request = new HttpEntity<>(checkoutToSave, adminHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(17)
    void shouldNotBorrowABookIfUserRequested() {
        ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
        HttpEntity<?> request = new HttpEntity<>(lendingToSave, userHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(18)
    void shouldNotBorrowABookIfUserIsNotAuthenticated() {
        ActionRequest checkoutToSave = createRequestBody(2L, "540200000001");
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(33)
    void shouldRenewABookIfAdminRequestedAndBookIsNotReservedAndDateIsOK() {
        String bookBarcode = "540200000002";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> lendingResponseBefore = restTemplate
                .exchange("/api/v1/lendings/2", HttpMethod.GET, request, String.class);
        LendingDto lendingBefore = getLendingFromResponse(lendingResponseBefore);

        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> lendingResponseAfter = restTemplate
                .exchange("/api/v1/lendings/2", HttpMethod.GET, request, String.class);
        LendingDto lendingAfter = getLendingFromResponse(lendingResponseAfter);

        assertThat(lendingBefore.getDueDate()).isNotEqualTo(lendingAfter.getDueDate());
    }

    @Test
    @Order(19)
    void shouldNotRenewABookIfAdminRequestedAndBookIsReserved() {
        String bookBarcode = "540200000007";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(20)
    void shouldNotRenewABookIfAdminRequestedAndDateIsNotOK() {
        String bookBarcode = "540200000003";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(22)
    void shouldNotRenewABookIfAdminRequestedAndBookItemDoesNotExist() {
        String bookBarcode = "540299999999";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(23)
    void shouldNotRenewABookIfAdminRequestedAndLendingDoesNotExist() {
        String bookBarcode = "540200000006";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(24)
    void shouldNotRenewABookIfUserRequested() {
        String bookBarcode = "540200000004";
        HttpEntity<?> request = new HttpEntity<>(userHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(25)
    void shouldNotRenewABookIfUserIsNotAuthenticated() {
        String bookBarcode = "540200000004";
        ResponseEntity<String> renewResponse = restTemplate
                .postForEntity("/api/v1/lendings/renew?bookBarcode=" + bookBarcode, HttpEntity.EMPTY, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(34)
    @CsvSource({
            "5, 540200000002, 2, 2, false",
            "1, 540200000003, 3, 3, true",
            "4, 540200000004, 4, 4, false",
            "1, 540200000007, 5, 7, false"
    })
    void shouldAllowToReturnABookIfAdminRequestedAndBookItemIsBorrowedAlready(
            Long memberId,
            String bookBarcode,
            Long lendingId,
            Long bookItemId,
            boolean isAfterDate
    ) {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        MemberDto memberBefore = findMemberById(memberId, request);

        BookItemDto bookItemBefore = findBookItemById(bookItemId);
        assertThat(bookItemBefore.getStatus()).isEqualTo(BookItemStatus.LOANED);

        ResponseEntity<String> renewResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookBarcode=" + bookBarcode, HttpMethod.PATCH, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> lendingResponse = restTemplate
                .exchange("/api/v1/lendings/" + lendingId, HttpMethod.GET, request, String.class);
        assertThat(lendingResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LendingDto lending = getLendingFromResponse(lendingResponse);
        assertThat(lending.getStatus()).isEqualTo(LendingStatus.COMPLETED);
        assertThat(lending.getReturnDate()).isNotNull();

        BookItemDto bookItemAfter = findBookItemById(bookItemId);
        assertThat(bookItemAfter.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.RESERVED);
        assertThat(bookItemAfter.getDueDate()).isEqualTo(lending.getReturnDate());

        MemberDto memberAfter = findMemberById(memberId, request);
        assertThat(memberAfter.getTotalBooksBorrowed()).isEqualTo(memberBefore.getTotalBooksBorrowed() - 1);
        if (isAfterDate) {
            assertThat(memberAfter.getCharge()).isGreaterThan(memberBefore.getCharge());
        } else {
            assertThat(memberAfter.getCharge()).isEqualTo(memberBefore.getCharge());
        }
    }

    @ParameterizedTest
    @Order(26)
    @CsvSource({
            "1, 540200000001",
            "6, 540200000006"
    })
    void shouldNotAllowToReturnABookIfAdminRequestedAndBookItemIsReturnedAlready(Long memberId, String bookBarcode) {
        ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
        HttpEntity<?> request = new HttpEntity<>(lendingToSave, adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookBarcode=" + bookBarcode, HttpMethod.PATCH, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(28)
    void shouldNotAllowToReturnABookIfAdminRequestedAndBookItemIdIsDoesNotExist() {
        String bookBarcode = "540299999999";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookBarcode=" + bookBarcode, HttpMethod.PATCH, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(29)
    void shouldNotReturnABookIfAdminRequestedAndLendingDoesNotExist() {
        String bookBarcode = "540200000006";
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookBarcode=" + bookBarcode, HttpMethod.PATCH, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(30)
    void shouldNotAllowToReturnABookItemIfUserRequested() {
        String bookBarcode = "540200000004";
        HttpEntity<?> request = new HttpEntity<>(userHeader);
        ResponseEntity<String> renewResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookBarcode=" + bookBarcode, HttpMethod.PATCH, request, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(31)
    void shouldNotAllowToReturnABookItemIfUserIsNotAuthenticated() {
        String bookBarcode = "540200000004";
        ResponseEntity<String> renewResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookBarcode=" + bookBarcode, HttpMethod.PATCH, HttpEntity.EMPTY, String.class);
        assertThat(renewResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private ActionRequest createRequestBody(Long userId, String bookBarcode) {
        return new ActionRequest(userId, bookBarcode);
    }

    private ReservationResponse getReservationFromResponse(ResponseEntity<String> response) {
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        ReservationResponse reservation = new ReservationResponse();
        MemberDto user = parseMemberDto(documentContext);
        BookItemDto book = parseBookItemDto(documentContext);
        reservation.setId(((Number)documentContext.read("$.id")).longValue());
        reservation.setCreationDate(LocalDate.parse(documentContext.read("$.creationDate")) );
        reservation.setStatus(ReservationStatus.valueOf(documentContext.read("$.status")));
        reservation.setMember(user);
        reservation.setBookItem(book);
        return reservation;
    }

    private LendingDto getLendingFromResponse(ResponseEntity<String> response) {
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        LendingDto lending = new LendingDto();
        MemberDto user = parseMemberDto(documentContext);
        BookItemDto book = parseBookItemDto(documentContext);
        lending.setId(((Number) documentContext.read("$.id")).longValue());
        lending.setCreationDate(LocalDate.parse(documentContext.read("$.creationDate")) );
        lending.setDueDate(LocalDate.parse(documentContext.read("$.dueDate")) );
        lending.setReturnDate(documentContext.read("$.returnDate") != null ? LocalDate.parse(documentContext.read("$.returnDate")) : null);
        lending.setStatus(LendingStatus.valueOf(documentContext.read("$.status")));
        lending.setMember(user);
        lending.setBookItem(book);
        return lending;
    }

    private static BookItemDto parseBookItemDto(DocumentContext documentContext) {
        BookItemDto dto = new BookItemDto();
        dto.setId(((Number) documentContext.read("$.bookItem.id")).longValue());
        dto.setBarcode(documentContext.read("$.bookItem.barcode"));
        dto.setIsReferenceOnly(documentContext.read("$.bookItem.isReferenceOnly"));
        dto.setBorrowed(LocalDate.parse(documentContext.read("$.bookItem.borrowed"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setDueDate(LocalDate.parse(documentContext.read("$.bookItem.dueDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setPrice(BigDecimal.valueOf(((Number) documentContext.read("$.bookItem.price")).doubleValue()));
        dto.setFormat(BookItemFormat.valueOf(documentContext.read("$.bookItem.format")));
        dto.setStatus(BookItemStatus.valueOf(documentContext.read("$.bookItem.status")));
        dto.setDateOfPurchase(LocalDate.parse(documentContext.read("$.bookItem.dateOfPurchase"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setPublicationDate(LocalDate.parse(documentContext.read("$.bookItem.publicationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Book book = new Book();
        book.setId(((Number) documentContext.read("$.bookItem.book.id")).longValue());
        book.setTitle(documentContext.read("$.bookItem.book.title"));
        book.setSubject(documentContext.read("$.bookItem.book.subject"));
        book.setPublisher(documentContext.read("$.bookItem.book.publisher"));
        book.setLanguage(documentContext.read("$.bookItem.book.language"));
        book.setPages(documentContext.read("$.bookItem.book.pages"));
        book.setISBN(documentContext.read("$.bookItem.book.isbn"));

        dto.setBook(BookMapper.map(book));
        return dto;
    }

    private static MemberDto parseMemberDto(DocumentContext documentContext) {
        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.member.card.id")).longValue());
        card.setBarcode(documentContext.read("$.member.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.member.card.issuedAt")));
        card.setActive(documentContext.read("$.member.card.active"));

        MemberDto user = new MemberDto();
        user.setId(((Number) documentContext.read("$.member.id")).longValue());
        user.setFirstName(documentContext.read("$.member.firstName"));
        user.setLastName(documentContext.read("$.member.lastName"));
        user.setEmail(documentContext.read("$.member.email"));
        user.setCard(card);
        return user;
    }

    private MemberDto findMemberById(Long memberId, HttpEntity<Object> request) {
        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/users/" + memberId, HttpMethod.GET, request, String.class);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.card.id")).longValue());
        card.setBarcode(documentContext.read("$.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.card.issuedAt")));
        card.setActive(documentContext.read("$.card.active"));

        MemberDto user = new MemberDto();
        user.setId(((Number) documentContext.read("$.id")).longValue());
        user.setFirstName(documentContext.read("$.firstName"));
        user.setLastName(documentContext.read("$.lastName"));
        user.setEmail(documentContext.read("$.email"));
        user.setCard(card);
        user.setDateOfMembership(LocalDate.parse(documentContext.read("$.dateOfMembership"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        user.setTotalBooksBorrowed(documentContext.read("$.totalBooksBorrowed"));
        user.setTotalBooksReserved(documentContext.read("$.totalBooksReserved"));
        user.setCharge(BigDecimal.valueOf(((Number) documentContext.read("$.charge")).doubleValue()));
        return user;
    }

    private BookItemDto findBookItemById(Long bookId) {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/book-items/" + bookId, String.class);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        BookItemDto dto = new BookItemDto();
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setBarcode(documentContext.read("$.barcode"));
        dto.setIsReferenceOnly(documentContext.read("$.isReferenceOnly"));
        dto.setBorrowed(LocalDate.parse(documentContext.read("$.borrowed"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setDueDate(LocalDate.parse(documentContext.read("$.dueDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setPrice(BigDecimal.valueOf(((Number) documentContext.read("$.price")).doubleValue()));
        dto.setFormat(BookItemFormat.valueOf(documentContext.read("$.format")));
        dto.setStatus(BookItemStatus.valueOf(documentContext.read("$.status")));
        dto.setDateOfPurchase(LocalDate.parse(documentContext.read("$.dateOfPurchase"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setPublicationDate(LocalDate.parse(documentContext.read("$.publicationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Book book = new Book();
        book.setId(((Number) documentContext.read("$.book.id")).longValue());
        book.setTitle(documentContext.read("$.book.title"));
        book.setSubject(documentContext.read("$.book.subject"));
        book.setPublisher(documentContext.read("$.book.publisher"));
        book.setLanguage(documentContext.read("$.book.language"));
        book.setPages(documentContext.read("$.book.pages"));
        book.setISBN(documentContext.read("$.book.isbn"));

        dto.setBook(BookMapper.map(book));
        return dto;
    }

    private HttpHeaders authenticate(LoginRequest user) {
        LoginResponse userLoginResponse = authenticationService.authenticate(user);
        String userToken = userLoginResponse.getToken();

        HttpHeaders userHeader = new HttpHeaders();
        userHeader.setBearerAuth(userToken);
        userHeader.setContentType(MediaType.APPLICATION_JSON);
        return userHeader;
    }
}
