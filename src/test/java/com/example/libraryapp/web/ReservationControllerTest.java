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
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.ActionRequest;
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
public class ReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    private HttpHeaders adminHeader;
    private HttpHeaders userHeader;

    @BeforeAll
     void authenticate() {
        LoginRequest admin = new LoginRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");
        LoginResponse adminLoginResponse = authenticationService.authenticate(admin);
        String adminToken = adminLoginResponse.getToken();

        HttpHeaders adminHeader = new HttpHeaders();
        adminHeader.setBearerAuth(adminToken);
        adminHeader.setContentType(MediaType.APPLICATION_JSON);
        this.adminHeader = adminHeader;

        LoginRequest user = new LoginRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");
        LoginResponse userLoginResponse = authenticationService.authenticate(user);
        String userToken = userLoginResponse.getToken();

        HttpHeaders userHeader = new HttpHeaders();
        userHeader.setBearerAuth(userToken);
        userHeader.setContentType(MediaType.APPLICATION_JSON);
        this.userHeader = userHeader;
    }

    @Test
    @Order(1)
    void shouldReturnAllReservationsIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int allReservationsLength = documentContext.read("$._embedded.reservationResponseList.length()");
        assertThat(allReservationsLength).isEqualTo(11);

        getResponse = restTemplate
                .exchange("/api/v1/reservations?memberId=2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(getResponse.getBody());
        int memberReservationsLength = documentContext.read("$._embedded.reservationResponseList.length()");
        assertThat(memberReservationsLength).isEqualTo(3);
    }

    @Test
    @Order(2)
    void shouldReturnPageOf3ReservationsIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/reservations?page=1&size=3", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.reservationResponseList.length()");
        assertThat(bookListLength).isEqualTo(3);
        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(3);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(11);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(4);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(1);
    }

    @Test
    @Order(3)
    void shouldReturnAllUsersReservationsIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations?memberId=2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int usersReservationsLength = documentContext.read("$._embedded.reservationResponseList.length()");
        assertThat(usersReservationsLength).isEqualTo(3);
    }

    @Test
    @Order(4)
    void shouldNotReturnMembersReservationsIfMemberIdDoesNotExist() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations?memberId=99999", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        getResponse = restTemplate
                .exchange("/api/v1/reservations?memberId=badrequest", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @Order(5)
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersReservationsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations?memberId=" + memberId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(6)
    void shouldNotReturnAllUsersReservationsIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(7)
    void shouldNotReturnAllReservationsIfUserIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/reservations", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        getResponse = restTemplate
                .getForEntity("/api/v1/reservations?memberId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(8)
    @CsvSource({
            "1, 1, 2",
            "2, 2, 4",
            "3, 3, 2",
            "4, 4, 1",
            "5, 5, 4",
            "6, 6, 5",
            "7, 7, 6",
            "8, 2, 4",
            "9, 2, 5",
            "10, 3, 4",
            "11, 3, 7"
    })
    void shouldReturnAnExistingReservationIfAdminRequested(Long reservationId, Long memberId, Long bookItemId) {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationResponse returnedReservation = getReservationFromResponse(getResponse);
        MemberDto member = findMemberById(memberId, request);
        BookItemDto book = findBookItemById(bookItemId);

        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getCreationDate()).isNotNull();
        assertThat(returnedReservation.getStatus()).isNotNull();
        assertThat(returnedReservation.getMember()).isEqualTo(member);
        assertThat(returnedReservation.getBookItem()).isEqualTo(book);
    }

    @Test
    @Order(9)
    void shouldReturnAnExistingReservationIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> adminRequest = new HttpEntity<>(adminHeader);
        HttpEntity<Object> userRequest = new HttpEntity<>(userHeader);
        MemberDto member = findMemberById(2L, adminRequest);
        BookItemDto book = findBookItemById(4L);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/2", HttpMethod.GET, userRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationResponse returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isEqualTo(2L);
        assertThat(returnedReservation.getCreationDate()).isEqualTo(LocalDate.parse("2023-05-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);
        assertThat(returnedReservation.getMember()).isEqualTo(member);
        assertThat(returnedReservation.getBookItem()).isEqualTo(book);
    }

    @ParameterizedTest
    @Order(10)
    @CsvSource({
            "1", "3", "4", "5", "6", "7", "10", "11"
    })
    void shouldNotReturnAnExistingReservationIfUserRequestedAndDoesNotOwnThisData(Long reservationId) {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(11)
    @CsvSource({
            "12", "123", "99999"
    })
    void shouldNotReturnReservationThatDoesNotExist(Long reservationId) {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @Order(12)
    @CsvSource({
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
    })
    void shouldNotReturnAnExistingReservationIfUserIsNotAuthenticated(Long reservationId) {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(21)
    void shouldMakeAReservationIfUserIsAuthenticated() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200000002");
        HttpEntity<Object> adminRequest = new HttpEntity<>(adminHeader);
        HttpEntity<Object> userRequest = new HttpEntity<>(reservationToSave, userHeader);
        MemberDto user = findMemberById(2L, adminRequest);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",userRequest, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newlyCreatedReservationLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .exchange(newlyCreatedReservationLocation, HttpMethod.GET, userRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookItemDto bookAfterReservation = findBookItemById(2L);
        ReservationResponse returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getCreationDate()).isNotNull();
        assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.PENDING);
        assertThat(returnedReservation.getMember()).isEqualTo(user);
        assertThat(returnedReservation.getBookItem()).isEqualTo(bookAfterReservation);
    }

    @Test
    @Order(13)
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIdDoesNotExist() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200099999");
        HttpEntity<Object> request = new HttpEntity<>(reservationToSave, userHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(14)
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndUserIdDoesNotExist() {
        ActionRequest reservationToSave = createPostRequestBody(999999999L, "540200000002");
        HttpEntity<Object> request = new HttpEntity<>(reservationToSave, userHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(15)
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedButUserIdIsNotTheir(Long userId) {
        ActionRequest reservationToSave = createPostRequestBody(userId, "540200000002");
        HttpEntity<Object> request = new HttpEntity<>(reservationToSave, userHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(16)
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookItemIsLost() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200000005");
        HttpEntity<Object> request = new HttpEntity<>(reservationToSave, userHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(17)
    void shouldNotMakeAReservationIfUserIsNotAuthenticated() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200000002");
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(24)
    void shouldNotMakeAReservationIfRequestBodyIsEmpty() {
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @Order(22)
    @CsvSource({
            "10, 3, 4, 540200000004, true",
            "11, 3, 7, 540200000007, false"
    })
    void shouldCancelAReservationIfAdminRequested(
            Long reservationId,
            Long memberId,
            Long bookItemId,
            String bookBarcode,
            Boolean isReservedBySomeoneElse
    ) {
        ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
        HttpEntity<Object> request = new HttpEntity<>(reservationToCancel, adminHeader);
        MemberDto memberBeforeResCanceling = findMemberById(memberId, request);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationResponse returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);

        MemberDto memberAfterResCanceling = findMemberById(memberId, request);
        assertThat(memberBeforeResCanceling.getTotalBooksReserved()).isEqualTo(memberAfterResCanceling.getTotalBooksReserved() + 1);

        BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);
        if (isReservedBySomeoneElse) {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.RESERVED, BookItemStatus.LOANED);
        } else {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.LOANED);
        }
    }

    @ParameterizedTest
    @Order(23)
    @CsvSource({
            "8, 2, 4, 540200000004, false",
            "9, 2, 1, 540200000001, false"
    })
    void shouldCancelAReservationIfUserRequestedAndReservationBelongsToTheir(
            Long reservationId,
            Long memberId,
            Long bookItemId,
            String bookBarcode,
            Boolean isReservedBySomeoneElse
    ) {
        ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
        HttpEntity<Object> request = new HttpEntity<>(reservationToCancel, userHeader);
        MemberDto memberBeforeResCanceling = findMemberById(memberId, request);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto memberAfterResCanceling = findMemberById(memberId, request);
        assertThat(memberBeforeResCanceling.getTotalBooksReserved()).isEqualTo(memberAfterResCanceling.getTotalBooksReserved() + 1);

        BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);
        if (isReservedBySomeoneElse) {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.RESERVED, BookItemStatus.LOANED);
        } else {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.LOANED);
        }
    }

    @ParameterizedTest
    @Order(18)
    @CsvSource({
            "1, 1, 2, 540200000002",
            "3, 3, 2, 540200000002",
            "4, 4, 1, 540200000001",
            "5, 5, 4, 540200000004",
            "6, 6, 5, 540200000005",
            "7, 7, 6, 540200000006",
            "10, 3, 4, 540200000004",
            "11, 3, 7, 540200000007"
    })
    void shouldNotCancelAReservationIfUserRequestedAndReservationIdIsNotTheir(
            Long reservationId,
            Long memberId,
            Long bookItemId,
            String bookBarcode
    ) {
        ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
        HttpEntity<Object> adminRequest = new HttpEntity<>(adminHeader);
        HttpEntity<Object> userRequest = new HttpEntity<>(reservationToCancel, userHeader);
        MemberDto memberBeforeResCanceling = findMemberById(memberId, adminRequest);
        BookItemDto bookItemBeforeResCanceling = findBookItemById(bookItemId);

        ResponseEntity<String> getResponseBeforeCanceling = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, adminRequest, String.class);
        assertThat(getResponseBeforeCanceling.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReservationResponse reservationBeforeCanceling = getReservationFromResponse(getResponseBeforeCanceling);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.DELETE, userRequest, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        ResponseEntity<String> getResponseAfterCanceling = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, adminRequest, String.class);
        assertThat(getResponseAfterCanceling.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReservationResponse reservationAfterCanceling = getReservationFromResponse(getResponseAfterCanceling);

        MemberDto memberAfterResCanceling = findMemberById(memberId, adminRequest);
        BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);

        assertThat(reservationBeforeCanceling).isEqualTo(reservationAfterCanceling);
        assertThat(memberBeforeResCanceling).isEqualTo(memberAfterResCanceling);
        assertThat(bookItemBeforeResCanceling).isEqualTo(bookItemAfterResCanceling);
    }

    @Test
    @Order(19)
    void shouldNotCancelAReservationIfUserIsNotAuthenticated() {
        ActionRequest reservationToCancel = createPostRequestBody(1L, "540200000001");
        HttpEntity<Object> request = new HttpEntity<>(reservationToCancel);
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(20)
    @CsvSource({
            "1, 540200000023",
            "876, 54020000002",
    })
    void shouldNotCancelAReservationThatDoesNotExist(Long memberId, String bookBarcode) {
        ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
        HttpEntity<Object> request = new HttpEntity<>(reservationToCancel, adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(25)
    void shouldNotCancelAReservationIfRequestBodyIsEmpty() {
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.DELETE, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private ActionRequest createPostRequestBody(Long userId, String bookBarcode) {
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
                .exchange("/api/v1/members/" + memberId, HttpMethod.GET, request, String.class);

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
}
