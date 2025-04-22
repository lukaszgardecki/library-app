package com.example.libraryapp.OLDweb;

import com.example.libraryapp.OLDdomain.exception.ErrorMessage;
import com.example.libraryapp.OLDdomain.notification.dto.NotificationDto;
import com.example.libraryapp.OLDmanagement.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class NotificationControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetNotificationsTests {
        @Test
        @DisplayName("Should return all notifications if ADMIN requested.")
        void shouldReturnAllNotificationsIfAdminRequested() {
            client.testRequest(GET, "/notifications", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.notificationDtoList.length()").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(27);
        }

        @Test
        @DisplayName("Should return a page of 3 notifications if ADMIN requested.")
        void shouldReturnPageOf3NotificationsIfAdminRequested() {
            client.testRequest(GET, "/notifications?page=1&size=3", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.notificationDtoList.length()").isEqualTo(3)
                    .jsonPath("$.page.size").isEqualTo(3)
                    .jsonPath("$.page.totalElements").isEqualTo(27)
                    .jsonPath("$.page.totalPages").isEqualTo(9)
                    .jsonPath("$.page.number").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return all member's notifications if ADMIN requested.")
        void shouldReturnAllUsersNotificationsIfAdminRequested() {
            client.testRequest(GET, "/notifications?memberId=5", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.notificationDtoList.length()").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(24);
        }

        @Test
        @DisplayName("Should return all member's notifications if USER requested and does own this data.")
        void shouldReturnAllUsersNotificationsIfUserRequestedAndDoesOwnThisData() {
            client.testRequest(GET, "/notifications?memberId=2", user, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(3);
        }

        @Test
        @DisplayName("Should not return member's notifications if ADMIN requested and user ID doesn't exist.")
        void shouldNotReturnAllUsersNotificationsIfUserIdDoesNotExist() {
            long memberId = 99999999;
            client.testRequest(GET, "/notifications?memberId=" + memberId, admin, OK)
                    .expectBody()
                    .jsonPath("_embedded").doesNotExist();

            client.testRequest(GET, "/notifications?memberId=badrequest", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class);
        }

        @ParameterizedTest
        @DisplayName("Should not return all member's notifications if USER requested and doesn't own this data")
        @CsvSource({
                "5"
        })
        void shouldNotReturnAllUsersNotificationsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
            ErrorMessage responseBody = client.testRequest(GET, "/notifications?memberId=" + memberId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all notifications if USER requested.")
        void shouldNotReturnAllUsersNotificationsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/notifications", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all notifications if an unauthorized USER requested.")
        void shouldNotReturnAllUsersNotificationsIfUserIsNotAuthenticated() {
            ErrorMessage responseBody1 = client.testRequest(GET, "/notifications", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody2 = client.testRequest(GET, "/notifications?memberId=2", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should return a notification if ADMIN requested.")
        @CsvSource({
                "1, 2",
                "2, 2",
                "3, 2",
                "4, 5",
                "5, 5",
                "6, 5",
        })
        void shouldReturnAnExistingNotificationIfAdminRequested(Long notificationId, Long memberId) {
            NotificationDto responseBody = client.testRequest(GET, "/notifications/" + notificationId, admin, OK)
                    .expectBody(NotificationDto.class)
                    .returnResult().getResponseBody();

            assertThat(responseBody.getId()).isEqualTo(notificationId);
            assertThat(responseBody.getCreatedAt()).isNotNull();
            assertThat(responseBody.getMemberId()).isEqualTo(memberId);
        }

        @Test
        @DisplayName("Should return a notification if USER requested and does own this data.")
        void shouldReturnAnExistingNotificationIfUserRequestedAndDoesOwnThisData() {
            NotificationDto responseBody = client.testRequest(GET, "/notifications/3", user, OK)
                    .expectBody(NotificationDto.class)
                    .returnResult().getResponseBody();

            assertThat(responseBody.getId()).isEqualTo(3);
            assertThat(responseBody.getCreatedAt()).isNotNull();
            assertThat(responseBody.getMemberId()).isEqualTo(2);
        }

        @ParameterizedTest
        @DisplayName("Should not return a notification if USER requested and doesn't own this data.")
        @CsvSource({
                "4", "5", "6"
        })
        void shouldNotReturnAnExistingNotificationIfUserRequestedAndDoesNotOwnThisData(Long notificationId) {
            ErrorMessage responseBody = client.testRequest(GET, "/notifications/" + notificationId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not return a notification if ADMIN requested and the ID is wrong.")
        @CsvSource({
                "57", "510", "99999"
        })
        void shouldNotReturnNotificationThatDoesNotExist(Long notificationId) {
            ErrorMessage responseBody = client.testRequest(GET, "/notifications/" + notificationId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.NOTIFICATION_NOT_FOUND.getMessage(notificationId)");
        }

        @ParameterizedTest
        @DisplayName("Should not return a notification if an unauthorized USER requested.")
        @CsvSource({
                "1", "2", "3", "4"
        })
        void shouldNotReturnAnExistingNotificationIfUserIsNotAuthenticated(Long notificationId) {
            ErrorMessage responseBody = client.testRequest(GET, "/notifications/" + notificationId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoints")
    class PostNotificationsTests {
        @Test
        @DisplayName("Should update notification 'read' field to true if ADMIN requested.")
        void shouldUpdateNotificationToReadTrueIfAdminRequested() {
            long notificationId = 1;
            client.testRequest(POST, "/notifications/" + notificationId, admin, OK)
                    .expectBody().isEmpty();

            NotificationDto responseBody = client.testRequest(GET, "/notifications/" + notificationId, admin, OK)
                    .expectBody(NotificationDto.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getRead()).isEqualTo(true);
        }

        @Test
        @DisplayName("Should update notification 'read' field to true if USER requested and does own this data.")
        void shouldUpdateNotificationToReadTrueIfUserRequestedAndDoesOwnThisData() {
            long notificationId = 1;
            client.testRequest(POST, "/notifications/" + notificationId, user, OK)
                    .expectBody().isEmpty();

            NotificationDto responseBody = client.testRequest(GET, "/notifications/" + notificationId, admin, OK)
                    .expectBody(NotificationDto.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getRead()).isEqualTo(true);
        }

        @ParameterizedTest
        @DisplayName("Should not update notification 'read' field to true if USER requested and does not own this data.")
        @CsvSource({
                "4, false",
                "5, false",
                "6, false",
                "7, false"
        })
        void shouldNotUpdateNotificationToReadTrueIfUserRequestedAndDoesNotOwnThisData(Long notificationId, boolean readBefore) {
            ErrorMessage responseBody = client.testRequest(POST, "/notifications/" + notificationId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");

            NotificationDto response = client.testRequest(GET, "/notifications/" + notificationId, admin, OK)
                    .expectBody(NotificationDto.class)
                    .returnResult().getResponseBody();
            assertThat(response.getRead()).isEqualTo(readBefore);
        }

        @ParameterizedTest
        @DisplayName("Should not update notification 'read' field to true if an unauthorized USER requested.")
        @CsvSource({
                "4, false",
                "5, false",
                "6, false",
                "7, false"
        })
        void shouldNotUpdateNotificationToReadTrueIfUnauthorizedUserRequested(Long notificationId, boolean readBefore) {
            ErrorMessage responseBody = client.testRequest(POST, "/notifications/" + notificationId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            NotificationDto response = client.testRequest(GET, "/notifications/" + notificationId, admin, OK)
                    .expectBody(NotificationDto.class)
                    .returnResult().getResponseBody();
            assertThat(response.getRead()).isEqualTo(readBefore);
        }
    }

    @Nested
    @DisplayName("Tests for DELETE endpoints")
    class DeleteNotificationsTests {
        @Test
        @DisplayName("Should delete a single notification if ADMIN requested.")
        void shouldDeleteNotificationIfAdminRequested() {
            long notificationId = 1;
            client.testRequest(DELETE, "/notifications/" + notificationId, admin, NO_CONTENT)
                    .expectBody().isEmpty();
            assertThatNotificationDoesNotExist(notificationId);
        }

        @Test
        @DisplayName("Should delete a single notification if USER requested and does own this data.")
        void shouldDeleteNotificationIfUserRequestedAndDoesOwnThisData() {
            long notificationId = 1;
            client.testRequest(DELETE, "/notifications/" + notificationId, user, NO_CONTENT)
                    .expectBody().isEmpty();
            assertThatNotificationDoesNotExist(notificationId);
        }

        @Test
        @DisplayName("Should not delete a single notification if USER requested and doesn't own this data.")
        void shouldNotDeleteNotificationIfUserRequestedAndDoesNotOwnThisData() {
            long notificationId = 4;
            ErrorMessage responseBody = client.testRequest(DELETE, "/notifications/" + notificationId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
            assertThatNotificationExists(notificationId);
        }

        @Test
        @DisplayName("Should not delete a single notification if an unauthorized USER requested.")
        void shouldNotDeleteNotificationIfUnauthorizedUserRequested() {
            long notificationId = 4;
            ErrorMessage responseBody = client.testRequest(DELETE, "/notifications/" + notificationId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
            assertThatNotificationExists(notificationId);
        }

        @Test
        @DisplayName("Should delete a couple of notifications if ADMIN requested.")
        void shouldDeleteCoupleOfNotificationsIfAdminRequested() {
            List<Long> notificationIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
            client.testRequest(DELETE, "/notifications", notificationIds, admin, NO_CONTENT)
                    .expectBody().isEmpty();

            notificationIds.forEach(el -> assertThatNotificationDoesNotExist(el));
        }

        @Test
        @DisplayName("Should delete a couple of notifications if USER requested and does own this data.")
        void shouldDeleteCoupleOfNotificationsIfUserRequestedAndDoesOwnThisData() {
            List<Long> notificationIds = List.of(1L, 2L, 3L);
            client.testRequest(DELETE, "/notifications", notificationIds, user, NO_CONTENT)
                    .expectBody().isEmpty();

            notificationIds.forEach(el -> assertThatNotificationDoesNotExist(el));
        }

        @Test
        @DisplayName("Should delete only the requesting USER's notifications if the request includes notification IDs belonging to other users.")
        void shouldDeleteOnlyRequestingUserNotificationsIfRequestIncludesOtherUsersIds() {
            List<Long> notificationIdsBelongsToUser = List.of(1L, 2L, 3L);
            List<Long> notificationIdsDoesNotBelongToUser = List.of(4L, 5L, 6L, 7L);
            List<Long> allIds = new ArrayList<>(notificationIdsBelongsToUser);
            allIds.addAll(notificationIdsDoesNotBelongToUser);

            client.testRequest(DELETE, "/notifications", allIds, user, NO_CONTENT)
                    .expectBody().isEmpty();

            notificationIdsBelongsToUser.forEach(el -> assertThatNotificationDoesNotExist(el));
            notificationIdsDoesNotBelongToUser.forEach(el -> assertThatNotificationExists(el));
        }

        @Test
        @DisplayName("Should not delete a couple of notifications if USER requested and doesn't own this data.")
        void shouldNotDeleteCoupleOfNotificationsIfUserRequestedAndDoesNotOwnThisData() {
            List<Long> notificationIds = List.of(4L, 5L, 6L, 7L);
            client.testRequest(DELETE, "/notifications", notificationIds, user, NO_CONTENT)
                    .expectBody().isEmpty();

            notificationIds.forEach(el -> assertThatNotificationExists(el));
        }

        @Test
        @DisplayName("Should not delete a couple of notifications if an unauthorized USER requested.")
        void shouldNotDeleteCoupleOfNotificationsIfUnauthorizedUserRequested() {
            List<Long> notificationIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
            ErrorMessage responseBody = client.testRequest(DELETE, "/notifications", notificationIds, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            notificationIds.forEach(el -> assertThatNotificationExists(el));
        }
    }

    private void assertThatNotificationExists(Long notificationId) {
        NotificationDto responseBody = client.testRequest(GET, "/notifications/" + notificationId, admin, OK)
                .expectBody(NotificationDto.class)
                .returnResult().getResponseBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isEqualTo(notificationId);
    }

    private void assertThatNotificationDoesNotExist(Long notificationId) {
        ErrorMessage responseBody = client.testRequest(GET, "/notifications/" + notificationId, admin, NOT_FOUND)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
        assertThat(responseBody.getMessage()).isEqualTo("Message.NOTIFICATION_NOT_FOUND.getMessage(notificationId)");
    }
}
