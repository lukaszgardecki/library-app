package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.rack.RackDto;
import com.example.libraryapp.domain.rack.RackToSaveDto;
import com.example.libraryapp.domain.rack.RackToUpdateDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance( TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class RackControllerTest {

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
    void shouldReturnAllRacksIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int rackListLength = documentContext.read("$._embedded.rackDtoList.length()");
        assertThat(rackListLength).isEqualTo(6);
    }

    @Test
    @Order(2)
    void shouldNotReturnAllRacksIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(3)
    void shouldNotReturnAllRacksIfUserIsNotAuthenticated() {
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(4)
    void shouldReturnRackIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        RackDto rack = parseRackDto(documentContext);
        assertThat(rack.getId()).isEqualTo(1L);
        assertThat(rack.getLocationIdentifier()).isEqualTo("123-I-12");
    }

    @Test
    @Order(5)
    void shouldNotReturnRackIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(6)
    void shouldNotReturnRackIfUserIsNotAuthenticated() {
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(7)
    void shouldReturnAllRackBookItemsIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> response2 = restTemplate.exchange("/api/v1/racks/5/book-items", HttpMethod.GET, request, String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext2 = JsonPath.parse(response2.getBody());
        int rackListLength2 = documentContext2.read("$.page.totalElements");
        assertThat(rackListLength2).isEqualTo(2);
    }

    @Test
    @Order(8)
    void shouldNotReturnAllRackBookItemsIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> response2 = restTemplate.exchange("/api/v1/racks/5/book-items", HttpMethod.GET, request, String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(9)
    void shouldNotReturnAllRackBookItemsIfUserIsNotAuthenticated() {
        ResponseEntity<String> response2 = restTemplate.exchange("/api/v1/racks/5/book-items", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(10)
    void shouldUpdateARackIfAdminRequested() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, adminHeader);

        ResponseEntity<String> getResponse = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContextBeforeUpdate = JsonPath.parse(getResponse.getBody());
        RackDto rackBeforeUpdate = parseRackDto(documentContextBeforeUpdate);

        ResponseEntity<String> updateResponse = restTemplate.exchange("/api/v1/racks/1", HttpMethod.PUT, request, String.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(updateResponse.getBody());
        RackDto rackAfterUpdate = parseRackDto(documentContext);

        assertThat(rackAfterUpdate.getId()).isEqualTo(rackBeforeUpdate.getId());
        assertThat(rackAfterUpdate.getLocationIdentifier()).isEqualTo("TestLocationID");
    }

    @Test
    @Order(11)
    void shouldNotUpdateARackIfUserRequested() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(12)
    void shouldNotUpdateARackIfUserIsNotAuthenticated() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(13)
    void shouldPartiallyUpdateARackIfAdminRequested() {
        RackToUpdateDto rackToUpdate = RackToUpdateDto.builder().locationIdentifier("TEST").build();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, adminHeader);

        ResponseEntity<String> getResponse = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContextBeforeUpdate = JsonPath.parse(getResponse.getBody());
        RackDto rackBeforeUpdate = parseRackDto(documentContextBeforeUpdate);

        ResponseEntity<String> updateResponse = restTemplate.exchange("/api/v1/racks/1", HttpMethod.PATCH, request, String.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(updateResponse.getBody());
        RackDto rackAfterUpdate = parseRackDto(documentContext);

        assertThat(rackAfterUpdate.getId()).isEqualTo(rackBeforeUpdate.getId());
        assertThat(rackAfterUpdate.getLocationIdentifier()).isEqualTo("TEST");
    }

    @Test
    @Order(14)
    void shouldNotPartiallyUpdateARackIfUserRequested() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.PATCH, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(15)
    void shouldNotPartiallyUpdateARackIfUserIsNotAuthenticated() {
        RackToUpdateDto rackToUpdate = getRackToUpdateDto();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.PATCH, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(16)
    void shouldAddARackIfAdminRequested() {
        RackToSaveDto rackToUpdate = RackToSaveDto.builder().locationIdentifier("NEW_RACK").build();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, adminHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(17)
    void shouldNotAddARackIfAdminRequestedAndLocationIdentifierAlreadyExists() {
        RackToSaveDto rackToUpdate = RackToSaveDto.builder().locationIdentifier("NEW_RACK").build();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, adminHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(18)
    void shouldNotAddARackIfUserRequested() {
        RackToSaveDto rackToUpdate = RackToSaveDto.builder().locationIdentifier("NEW_USER_RACK").build();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate, userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(19)
    void shouldNotAddARackIfUserIsNotAuthenticated() {
        RackToSaveDto rackToUpdate = RackToSaveDto.builder().locationIdentifier("NEW_UNAUTHENTICATED_RACK").build();
        HttpEntity<Object> request = new HttpEntity<>(rackToUpdate);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(20)
    void shouldNotDeleteARackIfAdminRequestedAndRackHasBookItems() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponseBefore = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, request, String.class);
        assertThat(getResponseBefore.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> deleteResponse = restTemplate.exchange("/api/v1/racks/1", HttpMethod.DELETE, request, String.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        ResponseEntity<String> getResponseAfter = restTemplate.exchange("/api/v1/racks/1", HttpMethod.GET, request, String.class);
        assertThat(getResponseAfter.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(21)
    void shouldDeleteARackIfAdminRequestedAndRackHasNotBookItems() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponseBefore = restTemplate.exchange("/api/v1/racks/5", HttpMethod.GET, request, String.class);
        assertThat(getResponseBefore.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> deleteRackBookItem1 = restTemplate.exchange("/api/v1/book-items/1", HttpMethod.DELETE, request, String.class);
        assertThat(deleteRackBookItem1.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<String> deleteRackBookItem2 = restTemplate.exchange("/api/v1/book-items/6", HttpMethod.DELETE, request, String.class);
        assertThat(deleteRackBookItem2.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> deleteResponse = restTemplate.exchange("/api/v1/racks/5", HttpMethod.DELETE, request, String.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponseAfter = restTemplate.exchange("/api/v1/racks/5", HttpMethod.GET, request, String.class);
        assertThat(getResponseAfter.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(22)
    void shouldNotDeleteARackIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.DELETE, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(23)
    void shouldNotDeleteARackIfUserIsNotAuthenticated() {
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/1", HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(24)
    void shouldSearchForARackIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> response1 = restTemplate.exchange("/api/v1/racks/search?q=123-I-12", HttpMethod.GET, request, String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext1 = JsonPath.parse(response1.getBody());
        int rackListLength1 = documentContext1.read("$.page.totalElements");
        assertThat(rackListLength1).isEqualTo(0);

        ResponseEntity<String> response2 = restTemplate.exchange("/api/v1/racks/search?q=123", HttpMethod.GET, request, String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext2 = JsonPath.parse(response2.getBody());
        int rackListLength2 = documentContext2.read("$.page.totalElements");
        assertThat(rackListLength2).isEqualTo(4);

        ResponseEntity<String> response3 = restTemplate.exchange("/api/v1/racks/search?q=123-I", HttpMethod.GET, request, String.class);
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext3 = JsonPath.parse(response3.getBody());
        int rackListLength3 = documentContext3.read("$.page.totalElements");
        assertThat(rackListLength3).isEqualTo(3);
    }

    @Test
    @Order(25)
    void shouldNotSearchForARackIfUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/search?q=123-I-12", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(26)
    void shouldNotSearchForARackIfUserIsNotAuthenticated() {
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/racks/search?q=123-I-12", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private RackDto parseRackDto(DocumentContext documentContext) {
        RackDto rackDto = new RackDto();
        rackDto.setId(((Number) documentContext.read("$.id")).longValue());
        rackDto.setLocationIdentifier(documentContext.read("$.locationIdentifier"));
        return rackDto;
    }

    private RackToUpdateDto getRackToUpdateDto() {
        return RackToUpdateDto.builder()
                .locationIdentifier("TestLocationID")
                .build();
    }
}
