package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.rack.RackDto;
import com.example.libraryapp.domain.rack.RackToSaveDto;
import com.example.libraryapp.domain.rack.RackToUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

public class RackControllerTest extends BaseTest{

    @Test
    void shouldReturnAllRacksIfAdminRequested() {
        client.get()
                .uri("/api/v1/racks")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.rackDtoList.length()").isEqualTo(7);
    }

    @Test
    void shouldNotReturnAllRacksIfUserRequested() {
        client.get()
                .uri("/api/v1/racks")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllRacksIfUserIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/racks")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldReturnRackIfAdminRequested() {
        RackDto rack = client.get()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class)
                .returnResult().getResponseBody();

        assertThat(rack.getId()).isEqualTo(1L);
        assertThat(rack.getLocationIdentifier()).isEqualTo("123-I-12");
    }

    @Test
    void shouldNotReturnRackIfUserRequested() {
        client.get()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnRackIfUserIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/racks/1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldReturnAllRackBookItemsIfAdminRequested() {
        client.get()
                .uri("/api/v1/racks/5/book-items")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.totalElements").isEqualTo(10);
    }

    @Test
    void shouldNotReturnAllRackBookItemsIfUserRequested() {
        client.get()
                .uri("/api/v1/racks/5/book-items")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllRackBookItemsIfUserIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/racks/5/book-items")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldUpdateARackIfAdminRequested() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();

        RackDto rackBeforeUpdate = client.get()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class)
                .returnResult().getResponseBody();

        RackDto rackAfterUpdate = client.put()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(rackToUpdate))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class)
                .returnResult().getResponseBody();

        assertThat(rackAfterUpdate.getId()).isEqualTo(rackBeforeUpdate.getId());
        assertThat(rackAfterUpdate.getLocationIdentifier()).isEqualTo("TestLocationID");
    }

    @Test
    void shouldNotUpdateARackIfUserRequested() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        client.put()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(rackToUpdate))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldNotUpdateARackIfUserIsNotAuthenticated() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        client.put()
                .uri("/api/v1/racks/1")
                .body(BodyInserters.fromValue(rackToUpdate))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldPartiallyUpdateARackIfAdminRequested() {
        RackToUpdateDto rackToUpdate = RackToUpdateDto.builder().locationIdentifier("TEST").build();

        RackDto rackBeforeUpdate = client.get()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class)
                .returnResult().getResponseBody();

        RackDto rackAfterUpdate = client.patch()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(rackToUpdate))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class)
                .returnResult().getResponseBody();

        assertThat(rackAfterUpdate.getId()).isEqualTo(rackBeforeUpdate.getId());
        assertThat(rackAfterUpdate.getLocationIdentifier()).isEqualTo("TEST");
    }

    @Test
    void shouldNotPartiallyUpdateARackIfUserRequested() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        client.patch()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(rackToUpdate))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPartiallyUpdateARackIfUserIsNotAuthenticated() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        client.patch()
                .uri("/api/v1/racks/1")
                .body(BodyInserters.fromValue(rackToUpdate))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldAddARackIfAdminRequested() {
        RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("NEW_RACK").build();
        client.post()
                .uri("/api/v1/racks")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(rackToAdd))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void shouldNotAddARackIfAdminRequestedAndLocationIdentifierAlreadyExists() {
        RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("123-I-12").build();
        client.post()
                .uri("/api/v1/racks")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(rackToAdd))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotAddARackIfUserRequested() {
        RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("NEW_USER_RACK").build();
        client.post()
                .uri("/api/v1/racks")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(rackToAdd))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotAddARackIfUserIsNotAuthenticated() {
        RackToSaveDto rackToAdd = RackToSaveDto.builder().locationIdentifier("NEW_UNAUTHENTICATED_RACK").build();
        client.post()
                .uri("/api/v1/racks")
                .body(BodyInserters.fromValue(rackToAdd))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteARackIfAdminRequestedAndRackHasBookItems() {
        client.get()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class);

        client.delete()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(RackDto.class);

        client.get()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class);
    }

    @Test
    void shouldDeleteARackIfAdminRequestedAndRackHasNotBookItems() {
        client.get()
                .uri("/api/v1/racks/7")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RackDto.class);

        client.delete()
                .uri("/api/v1/racks/7")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/api/v1/racks/7")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteARackIfUserRequested() {
        client.delete()
                .uri("/api/v1/racks/1")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteARackIfUserIsNotAuthenticated() {
        client.delete()
                .uri("/api/v1/racks/1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldSearchForARackIfAdminRequested() {
        client.get()
                .uri("/api/v1/racks/search?q=123-I-12")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.totalElements").isEqualTo(1);

        client.get()
                .uri("/api/v1/racks/search?q=123")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.totalElements").isEqualTo(7);

        client.get()
                .uri("/api/v1/racks/search?q=123-I")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.totalElements").isEqualTo(4);
    }

    @Test
    void shouldNotSearchForARackIfUserRequested() {
        client.get()
                .uri("/api/v1/racks/search?q=123-I-12")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotSearchForARackIfUserIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/racks/search?q=123-I-12")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    private RackToUpdateDto getRackToUpdateDto() {
        return RackToUpdateDto.builder()
                .locationIdentifier("TestLocationID")
                .build();
    }
}
