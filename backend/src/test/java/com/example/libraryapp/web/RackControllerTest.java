package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.rack.RackDto;
import com.example.libraryapp.domain.rack.RackToSaveDto;
import com.example.libraryapp.domain.rack.RackToUpdateDto;
import com.example.libraryapp.management.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class RackControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetRacksTests {
        @Test
        @DisplayName("Should return all racks if ADMIN requested.")
        void shouldReturnAllRacksIfAdminRequested() {
            client.testRequest(GET, "/racks", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.rackDtoList.length()").isEqualTo(7);
        }

        @Test
        @DisplayName("Should not return all racks if USER requested.")
        void shouldNotReturnAllRacksIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not return all racks if an unauthorized USER requested.")
        void shouldNotReturnAllRacksIfUserIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @Test
        @DisplayName("Should return a single rack if ADMIN requested.")
        void shouldReturnRackIfAdminRequested() {
            RackDto rack = client.testRequest(GET, "/racks/1", admin, OK)
                    .expectBody(RackDto.class)
                    .returnResult().getResponseBody();

            assertThat(rack.getId()).isEqualTo(1L);
            assertThat(rack.getLocationIdentifier()).isEqualTo("123-I-12");
        }

        @Test
        @DisplayName("Should not return a single rack if USER requested.")
        void shouldNotReturnRackIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks/1", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not return a single rack if an unauthorized USER requested.")
        void shouldNotReturnRackIfUserIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks/1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @Test
        @DisplayName("Should return all rack's book items if ADMIN requested.")
        void shouldReturnAllRackBookItemsIfAdminRequested() {
            client.testRequest(GET, "/racks/5/book-items", admin, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(10);
        }

        @Test
        @DisplayName("Should not return all rack's book items if USER requested.")
        void shouldNotReturnAllRackBookItemsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks/5/book-items", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not return all rack's book items if an unauthorized USER requested.")
        void shouldNotReturnAllRackBookItemsIfUserIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks/5/book-items", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @Test
        @DisplayName("Should search for a rack if ADMIN requested.")
        void shouldSearchForARackIfAdminRequested() {
            client.testRequest(GET, "/racks/search?q=123-I-12", admin, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(1);

            client.testRequest(GET, "/racks/search?q=123", admin, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(7);

            client.testRequest(GET, "/racks/search?q=123-I", admin, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(4);
        }

        @Test
        @DisplayName("Should not search for a rack if USER requested.")
        void shouldNotSearchForARackIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks/search?q=123-I-12", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not search for a rack if an unauthorized USER requested.")
        void shouldNotSearchForARackIfUserIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(GET, "/racks/search?q=123-I-12", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    @Nested
    @DisplayName("Tests for PUT endpoints")
    class UpdateRacksTests {
        @Test
        @DisplayName("Should update a rack if ADMIN requested.")
        void shouldUpdateARackIfAdminRequested() {
            RackToUpdateDto rackToUpdate = getRackToUpdateDto();

            RackDto rackBeforeUpdate = client.testRequest(GET, "/racks/1", admin, OK)
                    .expectBody(RackDto.class)
                    .returnResult().getResponseBody();

            RackDto rackAfterUpdate = client.testRequest(PUT, "/racks/1", rackToUpdate, admin, OK)
                    .expectBody(RackDto.class)
                    .returnResult().getResponseBody();

            assertThat(rackAfterUpdate.getId()).isEqualTo(rackBeforeUpdate.getId());
            assertThat(rackAfterUpdate.getLocationIdentifier()).isEqualTo("TestLocationID");
        }

        @Test
        @DisplayName("Should not update a rack if USER requested.")
        void shouldNotUpdateARackIfUserRequested() {
            RackToUpdateDto rackToUpdate = getRackToUpdateDto();
            ErrorMessage responseBody = client.testRequest(PUT, "/racks/1", rackToUpdate, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not update a rack if an unauthorized USER requested.")
        void shouldNotUpdateARackIfUserIsNotAuthenticated() {
            RackToUpdateDto rackToUpdate = getRackToUpdateDto();
            ErrorMessage responseBody = client.testRequest(PUT, "/racks/1", rackToUpdate, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    @Nested
    @DisplayName("Tests for PATCH endpoints")
    class PartialUpdateRacksTests {
        @Test
        @DisplayName("Should partially update a rack if ADMIN requested.")
        void shouldPartiallyUpdateARackIfAdminRequested() {
            RackToUpdateDto rackToUpdate = RackToUpdateDto.builder().locationIdentifier("TEST").build();

            RackDto rackBeforeUpdate = client.testRequest(GET, "/racks/1", admin, OK)
                    .expectBody(RackDto.class)
                    .returnResult().getResponseBody();

            RackDto rackAfterUpdate = client.testRequest(PATCH, "/racks/1", rackToUpdate, admin, OK)
                    .expectBody(RackDto.class)
                    .returnResult().getResponseBody();

            assertThat(rackAfterUpdate.getId()).isEqualTo(rackBeforeUpdate.getId());
            assertThat(rackAfterUpdate.getLocationIdentifier()).isEqualTo("TEST");
        }

        @Test
        @DisplayName("Should not partially update a rack if USER requested.")
        void shouldNotPartiallyUpdateARackIfUserRequested() {
            RackToUpdateDto rackToUpdate = getRackToUpdateDto();
            ErrorMessage responseBody = client.testRequest(PATCH, "/racks/1", rackToUpdate, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not partially update a rack if an unauthorized USER requested.")
        void shouldNotPartiallyUpdateARackIfUserIsNotAuthenticated() {
            RackToUpdateDto rackToUpdate = getRackToUpdateDto();
            ErrorMessage responseBody = client.testRequest(PATCH, "/racks/1", rackToUpdate, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoints")
    class AddRacksTests {
        @Test
        @DisplayName("Should add a rack if ADMIN requested.")
        void shouldAddARackIfAdminRequested() {
            RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("NEW_RACK").build();
            client.testRequest(POST, "/racks", rackToAdd, admin, CREATED)
                    .expectBody(RackDto.class);
        }

        @Test
        @DisplayName("Should not add a rack if ADMIN requested and location ID already exists.")
        void shouldNotAddARackIfAdminRequestedAndLocationIdentifierAlreadyExists() {
            String rackLocation = "123-I-12";
            RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier(rackLocation).build();
            ErrorMessage responseBody = client.testRequest(POST, "/racks", rackToAdd, admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.RACK_LOCATION_ALREADY_EXISTS.formatted(rackLocation));
        }

        @Test
        @DisplayName("Should not add a rack if USER requested.")
        void shouldNotAddARackIfUserRequested() {
            RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("NEW_USER_RACK").build();
            ErrorMessage responseBody = client.testRequest(POST, "/racks", rackToAdd, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not add a rack if an unauthorized USER requested.")
        void shouldNotAddARackIfUserIsNotAuthenticated() {
            RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("NEW_UNAUTHENTICATED_RACK").build();
            ErrorMessage responseBody = client.testRequest(POST, "/racks", rackToAdd, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    @Nested
    @DisplayName("Tests for DELETE endpoints")
    class DeleteRacksTests {
        @Test
        @DisplayName("Should delete a rack if ADMIN requested and the rack has no book items.")
        void shouldDeleteARackIfAdminRequestedAndRackHasNotBookItems() {
            long rackId = 7;
            client.testRequest(GET, "/racks/" + rackId, admin, OK)
                    .expectBody(RackDto.class);

            client.testRequest(DELETE, "/racks/" + rackId, admin, NO_CONTENT)
                    .expectBody().isEmpty();

            ErrorMessage responseBody = client.testRequest(GET, "/racks/" + rackId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.RACK_NOT_FOUND.formatted(rackId));
        }

        @Test
        @DisplayName("Should not delete a rack if ADMIN requested but the rack has book items.")
        void shouldNotDeleteARackIfAdminRequestedAndRackHasBookItems() {
            long rackId = 1;
            RackDto rackDto = client.testRequest(GET, "/racks/" + rackId, admin, OK)
                    .expectBody(RackDto.class)
                    .returnResult().getResponseBody();

            ErrorMessage responseBody = client.testRequest(DELETE, "/racks/" + rackId, admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.RACK_CANNOT_BE_DELETED.formatted(rackDto.getLocationIdentifier()));

            client.testRequest(GET, "/racks/" + rackId, admin, OK)
                    .expectBody(RackDto.class);
        }

        @Test
        @DisplayName("Should not delete a rack if USER requested.")
        void shouldNotDeleteARackIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/racks/1", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN);
        }

        @Test
        @DisplayName("Should not delete a rack if an unauthorized USER requested.")
        void shouldNotDeleteARackIfUserIsNotAuthenticated() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/racks/1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    private RackToUpdateDto getRackToUpdateDto() {
        return RackToUpdateDto.builder()
                .locationIdentifier("TestLocationID")
                .build();
    }
}
