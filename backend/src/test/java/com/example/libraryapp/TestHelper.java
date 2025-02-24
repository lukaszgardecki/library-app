package com.example.libraryapp;

import com.example.libraryapp.OLDweb.BaseTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

public class TestHelper {
    private final WebTestClient client;

    public TestHelper(WebTestClient client) {
        this.client = client;
    }

    public <T> WebTestClient.ResponseSpec testRequest(
            HttpMethod method, String URI, T body, @NonNull TestAuth auth, HttpStatus expectedStatus
    ) {
        return client.method(method)
                .uri(BaseTest.API_PREFIX + URI)
                .header(HttpHeaders.AUTHORIZATION, auth.getAccessToken())
                .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                .cookie(auth.getFgpCookie().getName(), auth.getFgpCookie().getValue())
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    public WebTestClient.ResponseSpec testRequest(
            HttpMethod method, String URI, @NonNull TestAuth auth, HttpStatus expectedStatus
    ) {
        return client.method(method)
                .uri(BaseTest.API_PREFIX + URI)
                .header(HttpHeaders.AUTHORIZATION, auth.getAccessToken())
                .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                .cookie(auth.getFgpCookie().getName(), auth.getFgpCookie().getValue())
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    public <T> WebTestClient.ResponseSpec testRequest(
            HttpMethod method, String URI, T body, HttpStatus expectedStatus
    ) {
        return client.method(method)
                .uri(BaseTest.API_PREFIX + URI)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    public WebTestClient.ResponseSpec testRequest(
            HttpMethod method, String URI, HttpStatus expectedStatus
    ) {
        return client.method(method)
                .uri(BaseTest.API_PREFIX + URI)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }
}
