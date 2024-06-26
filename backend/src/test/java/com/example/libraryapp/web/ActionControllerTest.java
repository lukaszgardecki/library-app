package com.example.libraryapp.web;

import com.example.libraryapp.domain.action.ActionDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.management.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;

public class ActionControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetActionsTests {
        @Test
        @DisplayName("Should return all actions if ADMIN requested.")
        void shouldReturnAllActionsIfAdminRequested() {
            PagedModel<ActionDto> responseBody = client.testRequest(GET, "/actions", admin, OK)
                    .expectBody(new ParameterizedTypeReference<PagedModel<ActionDto>>() {})
                    .returnResult()
                    .getResponseBody();
            assertThat(responseBody).isNotNull();
            assertThat(responseBody.getMetadata().getSize()).isEqualTo(20);
            assertThat(responseBody.getMetadata().getNumber()).isEqualTo(0);

        }

        @Test
        @DisplayName("Should return a page of 3 actions if ADMIN requested.")
        void shouldReturnPageOf3ActionsIfAdminRequested() {
            PagedModel<ActionDto> responseBody = client.testRequest(GET, "/actions?page=1&size=3", admin, OK)
                    .expectBody(new ParameterizedTypeReference<PagedModel<ActionDto>>() {})
                    .returnResult()
                    .getResponseBody();
            assertThat(responseBody).isNotNull();
            assertThat(responseBody.getMetadata().getSize()).isEqualTo(3);
        }

        @ParameterizedTest
        @DisplayName("Should return all member's actions if ADMIN requested.")
        @CsvSource({
                ", 1",
                ", 2",
                "LOGIN, 1",
                "LOGIN, 2",
                "REQUEST_NEW, 1",
                "REQUEST_NEW, 2",
                "NOTIFICATION_EMAIL, 1",
                "NOTIFICATION_EMAIL, 2",
                "NOTIFICATION_SMS, 1",
                "NOTIFICATION_SMS, 2",
                "REQUEST_COMPLETED, 1",
                "REQUEST_COMPLETED, 2",
                "BOOK_BORROWED, 1",
                "BOOK_BORROWED, 2",
                "BOOK_RENEWED, 1",
                "BOOK_RENEWED, 2",
                "BOOK_RETURNED, 1",
                "BOOK_RETURNED, 2",
                "LOGOUT, 1",
                "LOGOUT, 2"
        })
        void shouldReturnAllUsersActionsIfAdminRequested(String type, Long memberId) {
            PagedModel<ActionDto> responseBody = client.testRequest(GET, "/actions?memberId=%s&type=%s".formatted(memberId, type), admin, OK)
                    .expectBody(new ParameterizedTypeReference<PagedModel<ActionDto>>() {})
                    .returnResult()
                    .getResponseBody();
            responseBody.forEach(el -> {
                assertThat(el.getMemberId()).isEqualTo(memberId);
                if (type != null) {
                    assertThat(el.getType()).isEqualTo(type);
                }
            });
        }

        @Test
        @DisplayName("Should return all user's actions if USER requested and does own this data.")
        void shouldReturnAllUsersActionsIfUserRequestedAndDoesOwnThisData() {
            Long memberId = 2L;
            PagedModel<ActionDto> responseBody = client.testRequest(GET, "/actions?memberId=" + memberId, user, OK)
                    .expectBody(new ParameterizedTypeReference<PagedModel<ActionDto>>() {})
                    .returnResult()
                    .getResponseBody();
            responseBody.forEach(el -> assertThat(el.getMemberId()).isEqualTo(memberId));
        }

        @Test
        @DisplayName("Should not return member's actions if ADMIN requested and member ID doesn't exist.")
        void shouldNotReturnMembersActionsIfMemberIdDoesNotExist() {
            long memberId = 99999999;
            client.testRequest(GET, "/actions?memberId=" + memberId, admin, OK)
                    .expectBody()
                    .jsonPath("_embedded").doesNotExist();

            client.testRequest(GET, "/actions?memberId=badrequest", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class);
        }

        @ParameterizedTest
        @DisplayName("Should not return member's actions if USER requested and doesn't own this data.")
        @CsvSource({
                "1",
        })
        void shouldNotReturnAllUsersActionsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
            ErrorMessage responseBody = client.testRequest(GET, "/actions?memberId=" + memberId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not return all actions if USER requested.")
        void shouldNotReturnAllUsersActionsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/actions", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not return all actions if an unauthorized USER requested.")
        void shouldNotReturnAllActionsIfUserIsNotAuthenticated() {
            ErrorMessage responseBody1 = client.testRequest(GET, "/actions", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody2 = client.testRequest(GET, "/actions?memberId=1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @ParameterizedTest
        @DisplayName("Should return an action if ADMIN requested.")
        @CsvSource({
                "1, 1",
                "17, 1",
                "18, 2",
                "34, 2",
        })
        void shouldReturnAnExistingActionIfAdminRequested(Long actionId, Long memberId) {
            ActionDto returnedAction = client.testRequest(GET, "/actions/" + actionId, admin, OK)
                    .expectBody(ActionDto.class)
                    .returnResult().getResponseBody();

            assertThat(returnedAction.getId()).isEqualTo(actionId);
            assertThat(returnedAction.getMemberId()).isEqualTo(memberId);
            assertThat(returnedAction.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should return an action if USER requested and does own this data.")
        void shouldReturnAnExistingActionIfUserRequestedAndDoesOwnThisData() {
            ActionDto returnedAction = client.testRequest(GET, "/actions/18", user, OK)
                    .expectBody(ActionDto.class)
                    .returnResult().getResponseBody();

            assertThat(returnedAction.getId()).isEqualTo(18);
            assertThat(returnedAction.getMemberId()).isEqualTo(2);
            assertThat(returnedAction.getCreatedAt()).isNotNull();
        }

        @ParameterizedTest
        @DisplayName("Should not return an action if USER requested and doesn't own this data.")
        @CsvSource({
                "1", "2", "3", "4", "10", "12", "15", "17"
        })
        void shouldNotReturnAnExistingActionIfUserRequestedAndDoesNotOwnThisData(Long actionId) {
            ErrorMessage responseBody = client.testRequest(GET, "/actions/" + actionId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @ParameterizedTest
        @DisplayName("Should not return an action if ADMIN requested and an ID doesn't exist.")
        @CsvSource({
                "510", "99999"
        })
        void shouldNotReturnActionThatDoesNotExist(Long actionId) {
            ErrorMessage responseBody = client.testRequest(GET, "/actions/" + actionId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACTION_NOT_FOUND_BY_ID.formatted(actionId));
        }

        @ParameterizedTest
        @DisplayName("Should not return an action if an unauthorized USER requested.")
        @CsvSource({
                "1", "2", "3", "4", "5", "16", "22", "29", "34"
        })
        void shouldNotReturnAnExistingActionIfUserIsNotAuthenticated(Long actionId) {
            ErrorMessage responseBody = client.testRequest(GET, "/actions/" + actionId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }
}
