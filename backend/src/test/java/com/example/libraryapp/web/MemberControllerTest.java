package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.management.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberControllerTest extends BaseTest {

    @Test
    void shouldReturnAllUsersWhenAdminRequested() {
        client.get()
                .uri("/api/v1/members")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.memberDtoList.length()").isEqualTo(8)
                .jsonPath("$.page.size").isEqualTo(20)
                .jsonPath("$.page.totalElements").isEqualTo(8)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);

    }

    @Test
    void shouldNotReturnAllUsersWhenUserRequested() {
        client.get()
                .uri("/api/v1/members")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllUsersWhenRequestIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/members")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldReturnAnExistingUserDataIfAdminRequested() {
        MemberDto returnedUser = client.get()
                .uri("/api/v1/members/3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        assertThat(returnedUser.getId()).isEqualTo(3);
        assertThat(returnedUser.getFirstName()).isEqualTo("Adam");
        assertThat(returnedUser.getLastName()).isEqualTo("Mickiewicz");
        assertThat(returnedUser.getEmail()).isEqualTo("a.mickiewicz@gmail.com");
        assertThat(returnedUser.getDateOfMembership()).isEqualTo("2023-05-23");
        assertThat(returnedUser.getTotalBooksBorrowed()).isEqualTo(5);
        assertThat(returnedUser.getTotalBooksReserved()).isEqualTo(1);
        assertThat(returnedUser.getCharge()).isEqualTo(new BigDecimal("0.00"));
        assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000003");
    }

    @Test
    void shouldReturnAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        MemberDto returnedUser = client.get()
                .uri("/api/v1/members/2")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        assertThat(returnedUser.getId()).isEqualTo(2);
        assertThat(returnedUser.getFirstName()).isEqualTo("Kamil");
        assertThat(returnedUser.getLastName()).isEqualTo("Nielubi");
        assertThat(returnedUser.getEmail()).isEqualTo("user@example.com");
        assertThat(returnedUser.getDateOfMembership()).isEqualTo("2023-05-22");
        assertThat(returnedUser.getTotalBooksBorrowed()).isEqualTo(1);
        assertThat(returnedUser.getTotalBooksReserved()).isEqualTo(1);
        assertThat(returnedUser.getCharge()).isEqualTo(new BigDecimal("0.00"));
        assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000002");
    }

    @Test
    void shouldNotReturnAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        client.get()
                .uri("/api/v1/members/1")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnUserDataThatDoesNotExist() {
        client.get()
                .uri("/api/v1/members/999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnUserDataIfRequestIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/members/1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/members/9999999")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldPartiallyUpdateAnExistingUserDataIfAdminRequested() {
        long memberIdToUpdate = 2L;
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();

        MemberDto userBeforeUpdate = client.get()
                .uri("/api/v1/members/" + memberIdToUpdate)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        client.patch()
                .uri("/api/v1/members/" + memberIdToUpdate)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(userFieldsToUpdate))
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        MemberDto userAfterUpdate = client.get()
                .uri("/api/v1/members/" + memberIdToUpdate)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        assertThat(userAfterUpdate.getId()).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());
        assertThat(userAfterUpdate.getEmail()).isEqualTo(userBeforeUpdate.getEmail());
        assertThat(userAfterUpdate.getDateOfMembership()).isEqualTo(userBeforeUpdate.getDateOfMembership());
        assertThat(userAfterUpdate.getTotalBooksBorrowed()).isEqualTo(userBeforeUpdate.getTotalBooksBorrowed());
        assertThat(userAfterUpdate.getTotalBooksReserved()).isEqualTo(userBeforeUpdate.getTotalBooksReserved());
        assertThat(userAfterUpdate.getCharge()).isEqualTo(userBeforeUpdate.getCharge());

        assertThat(userAfterUpdate.getCard().getId()).isEqualTo(userBeforeUpdate.getCard().getId());
        assertThat(userAfterUpdate.getCard().getIssuedAt()).isEqualTo(userBeforeUpdate.getCard().getIssuedAt());
        assertThat(userAfterUpdate.getCard().getBarcode()).isEqualTo(userBeforeUpdate.getCard().getBarcode());
    }

    @Test
    void shouldNotPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        client.patch()
                .uri("/api/v1/members/3")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(userFieldsToUpdate))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        long memberIdToUpdate = 2L;
        MemberUpdateDto userFieldsToUpdate = new MemberUpdateDto();
        userFieldsToUpdate.setFirstName("Testimie");
        userFieldsToUpdate.setLastName("Testnazwisko");

        MemberDto userBeforeUpdate = client.get()
                .uri("/api/v1/members/" + memberIdToUpdate)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        client.patch()
                .uri("/api/v1/members/" + memberIdToUpdate)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(userFieldsToUpdate))
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        MemberDto userAfterUpdate = client.get()
                .uri("/api/v1/members/" + memberIdToUpdate)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());

        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getEmail()).isEqualTo(userBeforeUpdate.getEmail());
        assertThat(userAfterUpdate.getDateOfMembership()).isEqualTo(userBeforeUpdate.getDateOfMembership());
        assertThat(userAfterUpdate.getTotalBooksBorrowed()).isEqualTo(userBeforeUpdate.getTotalBooksBorrowed());
        assertThat(userAfterUpdate.getTotalBooksReserved()).isEqualTo(userBeforeUpdate.getTotalBooksReserved());
        assertThat(userAfterUpdate.getCharge()).isEqualTo(userBeforeUpdate.getCharge());

        assertThat(userAfterUpdate.getCard().getId()).isEqualTo(userBeforeUpdate.getCard().getId());
        assertThat(userAfterUpdate.getCard().getIssuedAt()).isEqualTo(userBeforeUpdate.getCard().getIssuedAt());
        assertThat(userAfterUpdate.getCard().getBarcode()).isEqualTo(userBeforeUpdate.getCard().getBarcode());
    }

    @Test
    void shouldNotPartiallyUpdateUserDataThatDoesNotExist() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        client.patch()
                .uri("/api/v1/members/999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(userFieldsToUpdate))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPartiallyUpdateUserDataIfRequestIsNotAuthenticated() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        client.patch()
                .uri("/api/v1/members/999999")
                .body(BodyInserters.fromValue(userFieldsToUpdate))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPartiallyUpdateUserDataIfRequestBodyIsEmpty() {
        client.patch()
                .uri("/api/v1/members/2")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldDeleteAnExistingUserIfAdminRequested() {
        long memberIdToDelete = 4L;

        client.delete()
                .uri("/api/v1/members/" + memberIdToDelete)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/api/v1/members/" + memberIdToDelete)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/reservations/13")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo(ReservationStatus.CANCELED.toString());

        client.get()
                .uri("/api/v1/book-items/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo(BookItemStatus.AVAILABLE.toString());
    }

    @Test
    void shouldNotDeleteAnExistingUserWhichHasNotReturnedTheBooks() {
        client.delete()
                .uri("/api/v1/members/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);

        client.delete()
                .uri("/api/v1/members/3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteAnExistingUserIfTheyHaveUnsettledFine() {
        client.delete()
                .uri("/api/v1/members/8")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteAnExistingUserIfUserRequestedAndDoesNotOwnThisData() {
        client.delete()
                .uri("/api/v1/members/1")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldDeleteAnExistingUserIfUserRequestedAndDoesOwnThisData() {
        long memberIdToDelete = 5L;

        client.delete()
                .uri("/api/v1/members/" + memberIdToDelete)
                .header(HttpHeaders.AUTHORIZATION, user5Token)
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/api/v1/members/" + memberIdToDelete)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/reservations/14")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo(ReservationStatus.CANCELED.toString());

        client.get()
                .uri("/api/v1/book-items/21")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo(BookItemStatus.AVAILABLE.toString());
    }

    @Test
    void shouldNotDeleteUserThatDoesNotExist() {
        client.delete()
                .uri("/api/v1/members/999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteUserIfRequestIsNotAuthenticated() {
        client.delete()
                .uri("/api/v1/members/1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    private MemberUpdateDto getMemberDtoToPartialUpdate() {
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setFirstName("Kunegunda");
        dto.setLastName("Niewiadomska");
        dto.setPassword("passss");
        return dto;
    }
}
