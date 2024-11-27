package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.member.AccountStatus;
import com.example.libraryapp.domain.member.Gender;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.management.Constants;
import com.example.libraryapp.management.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class MemberControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetMembersTests {
        @Test
        @DisplayName("Should return all members if ADMIN requested.")
        void shouldReturnAllUsersWhenAdminRequested() {
            client.testRequest(GET, "/members", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.memberListPreviewDtoAdminList.length()").isEqualTo(8)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(8)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);

        }

        @Test
        @DisplayName("Should return a filtered list of members if ADMIN requested.")
        void shouldReturnFilteredListOfMembersWhenAdminRequested() {
            String queryString1 = "kle";
            client.testRequest(GET, "/members?q=" + queryString1, admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.memberListPreviewDtoAdminList.length()").isEqualTo(1)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(1)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);

            String queryString2 = "kas";
            client.testRequest(GET, "/members?q=" + queryString2, admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.memberListPreviewDtoAdminList.length()").isEqualTo(1)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(1)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);

            String queryString3 = ".com";
            client.testRequest(GET, "/members?q=" + queryString3, admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.memberListPreviewDtoAdminList.length()").isEqualTo(8)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(8)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);

            String queryString4 = "2";
            client.testRequest(GET, "/members?q=" + queryString4, admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.memberListPreviewDtoAdminList.length()").isEqualTo(1)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(1)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should not return all members if USER requested.")
        void shouldNotReturnAllUsersWhenUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/members", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN.getMessage());
        }

        @Test
        @DisplayName("Should not return all members if an unauthorized USER requested.")
        void shouldNotReturnAllUsersWhenRequestIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(GET, "/members", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
        }

        @Test
        @DisplayName("Should return a single member if ADMIN requested.")
        void shouldReturnAnExistingUserDataIfAdminRequested() {
            MemberDto returnedUser = client.testRequest(GET, "/members/3", admin, OK)
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
            assertThat(returnedUser.getGender()).isEqualTo(Gender.OTHER);
            assertThat(returnedUser.getDateOfBirth()).isEqualTo("1993-03-14");
            assertThat(returnedUser.getStatus()).isEqualTo(AccountStatus.CLOSED);
            assertThat(returnedUser.getPhoneNumber()).isEqualTo("333-333-333");
            assertThat(returnedUser.getPesel()).isEqualTo("93031412345");
            assertThat(returnedUser.getNationality()).isEqualTo("Hiszpańskie");
            assertThat(returnedUser.getParentsNames()).isEqualTo("Anna, Przemysław");
            assertThat(returnedUser.getAddress()).isEqualTo("Witosa 23/402, 33-333 Katowice, Śląsk, Hiszpania");
            assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000003");
        }

        @Test
        @DisplayName("Should return a single member if USER (owner) requested.")
        void shouldReturnAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
            MemberDto returnedUser = client.testRequest(GET, "/members/2", user, OK)
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
        @DisplayName("Should not return a single member if USER (not owner) requested.")
        void shouldNotReturnAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
            ErrorMessage responseBody = client.testRequest(GET, "/members/1", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN.getMessage());
        }

        @Test
        @DisplayName("Should not return a single member if ID doesn't exist.")
        void shouldNotReturnUserDataThatDoesNotExist() {
            long memberId = 999999;
            ErrorMessage responseBody = client.testRequest(GET, "/members/" + memberId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.MEMBER_NOT_FOUND.getMessage(memberId));
        }

        @Test
        @DisplayName("Should not return a single member if an unauthorized USER requested.")
        void shouldNotReturnUserDataIfRequestIsNotAuthenticated() {
            ErrorMessage responseBody1 = client.testRequest(GET, "/members/1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());

            ErrorMessage responseBody2 = client.testRequest(GET, "/members/9999999", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for PATCH endpoints")
    class PartialUpdateMembersTests {
        @Test
        @DisplayName("Should partially update a member if ADMIN requested.")
        void shouldPartiallyUpdateAnExistingUserDataIfAdminRequested() {
            long memberIdToUpdate = 2L;
            MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();

            MemberDto userBeforeUpdate = client.testRequest(GET, "/members/" + memberIdToUpdate, admin, OK)
                    .expectBody(MemberDto.class)
                    .returnResult().getResponseBody();

            client.testRequest(PATCH, "/members/" + memberIdToUpdate, userFieldsToUpdate, admin, OK)
                    .expectBody(MemberDto.class)
                    .returnResult().getResponseBody();

            MemberDto userAfterUpdate = client.testRequest(GET, "/members/" + memberIdToUpdate, admin, OK)
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
        @DisplayName("Should not partially update a member if USER (not owner) requested.")
        void shouldNotPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
            MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
            ErrorMessage responseBody = client.testRequest(PATCH, "/members/3", userFieldsToUpdate, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN.getMessage());
        }

        @Test
        @DisplayName("Should partially update a member if USER (owner) requested.")
        void shouldPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
            long memberIdToUpdate = 2L;
            MemberUpdateDto userFieldsToUpdate = new MemberUpdateDto();
            userFieldsToUpdate.setFirstName("Testimie");
            userFieldsToUpdate.setLastName("Testnazwisko");

            MemberDto userBeforeUpdate = client.testRequest(GET, "/members/" + memberIdToUpdate, user, OK)
                    .expectBody(MemberDto.class)
                    .returnResult().getResponseBody();

            client.testRequest(PATCH, "/members/" + memberIdToUpdate, userFieldsToUpdate, user, OK)
                    .expectBody(MemberDto.class)
                    .returnResult().getResponseBody();

            MemberDto userAfterUpdate = client.testRequest(GET, "/members/" + memberIdToUpdate, user, OK)
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
        @DisplayName("Should not partially update a member if ID doesn't exist.")
        void shouldNotPartiallyUpdateUserDataThatDoesNotExist() {
            MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
            long memberId = 999999;
            ErrorMessage responseBody = client.testRequest(PATCH, "/members/" + memberId, userFieldsToUpdate, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.MEMBER_NOT_FOUND.getMessage(memberId));
        }

        @Test
        @DisplayName("Should not partially update a member if an unauthorized USER requested.")
        void shouldNotPartiallyUpdateUserDataIfRequestIsNotAuthenticated() {
            MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
            ErrorMessage responseBody = client.testRequest(PATCH, "/members/999999", userFieldsToUpdate, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
        }

        @Test
        @DisplayName("Should not partially update a member if a request body is missing.")
        void shouldNotPartiallyUpdateUserDataIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(PATCH, "/members/2", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.BODY_MISSING.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for DELETE endpoints")
    class DeleteMembersTests {
        @Test
        @DisplayName("Should delete a member if ADMIN requested.")
        void shouldDeleteAnExistingUserIfAdminRequested() {
            long memberId = 4L;

            client.testRequest(DELETE, "/members/" + memberId, admin, NO_CONTENT)
                    .expectBody().isEmpty();

            ErrorMessage responseBody = client.testRequest(GET, "/members/" + memberId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.MEMBER_NOT_FOUND.getMessage(memberId));

            client.testRequest(GET, "/reservations/13", admin, OK)
                    .expectBody()
                    .jsonPath("$.status").isEqualTo(ReservationStatus.CANCELED.toString());

            client.testRequest(GET, "/book-items/1", admin, OK)
                    .expectBody()
                    .jsonPath("$.status").isEqualTo(BookItemStatus.AVAILABLE.toString());
        }

        @Test
        @DisplayName("Should not delete a member if they haven't returned the books.")
        void shouldNotDeleteAnExistingUserWhichHasNotReturnedTheBooks() {
            ErrorMessage responseBody1 = client.testRequest(DELETE, "/members/1", admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo(Message.MEMBER_NOT_RETURNED_BOOKS.getMessage());

            ErrorMessage responseBody2 = client.testRequest(DELETE, "/members/3", admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo(Message.MEMBER_NOT_RETURNED_BOOKS.getMessage());
        }

        @Test
        @DisplayName("Should not delete a member if they have the unsettled fines.")
        void shouldNotDeleteAnExistingUserIfTheyHaveUnsettledFine() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/members/8", admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.MEMBER_UNSETTLED_CHARGES.getMessage());
        }

        @Test
        @DisplayName("Should not delete a member if USER (not owner) requested.")
        void shouldNotDeleteAnExistingUserIfUserRequestedAndDoesNotOwnThisData() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/members/1", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN.getMessage());
        }

        @Test
        @DisplayName("Should delete a member if USER (owner) requested.")
        void shouldDeleteAnExistingUserIfUserRequestedAndDoesOwnThisData() {
            long memberId = 5L;

            client.testRequest(DELETE, "/members/" + memberId, user5, NO_CONTENT)
                    .expectBody().isEmpty();

            ErrorMessage responseBody = client.testRequest(GET, "/members/" + memberId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.MEMBER_NOT_FOUND.getMessage(memberId));

            client.testRequest(GET, "/reservations/14", admin, OK)
                    .expectBody()
                    .jsonPath("$.status").isEqualTo(ReservationStatus.CANCELED.toString());

            client.testRequest(GET, "/book-items/21", admin, OK)
                    .expectBody()
                    .jsonPath("$.status").isEqualTo(BookItemStatus.AVAILABLE.toString());
        }

        @Test
        @DisplayName("Should not delete a member if ID doesn't exist.")
        void shouldNotDeleteUserThatDoesNotExist() {
            long memberId = 999999;
            ErrorMessage responseBody = client.testRequest(DELETE, "/members/" + memberId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.MEMBER_NOT_FOUND.getMessage(memberId));
        }

        @Test
        @DisplayName("Should not delete a member if an unauthorized USER requested.")
        void shouldNotDeleteUserIfRequestIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/members/1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
        }
    }

    private MemberUpdateDto getMemberDtoToPartialUpdate() {
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setFirstName("Kunegunda");
        dto.setLastName("Niewiadomska");
        dto.setPassword("passss");
        return dto;
    }
}
