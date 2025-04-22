package com.example.libraryapp.OLDweb;

import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.domain.auth.dto.LoginRequest;
import com.example.libraryapp.domain.auth.dto.LoginResponse;
import com.example.libraryapp.TestAuth;
import com.example.libraryapp.TestHelper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public abstract class BaseTest {
    public static final String API_PREFIX = "/api/v1";

    @Autowired
    protected AuthenticationFacade authenticationFacade;
    @Autowired
    protected WebTestClient testClient;
    protected TestHelper client;

    protected TestAuth admin;
    protected TestAuth user;
    protected TestAuth user5;
    protected TestAuth warehouse;
    protected TestAuth cashier;

    @BeforeAll
    void authenticate() {
        this.client = new TestHelper(testClient);
        this.admin = auth("admin@example.com", "adminpass");
        this.user = auth("user@example.com", "userpass");
        this.user5 = auth("p.smerf@gmail.com", "userpass3");
        this.warehouse = auth("m.zul@gmail.com", "userpass5");
        this.cashier = auth("l.gaga@gmail.com", "userpass4");
    }

    private TestAuth auth(String username, String password) {
        LoginRequest user = new LoginRequest();
        user.setUsername(username);
        user.setPassword(password);

        EntityExchangeResult<LoginResponse> response = client.testRequest(POST, "/authenticate", user, OK)
                .expectBody(LoginResponse.class)
                .returnResult();

        String accessToken = response.getResponseBody().getAccessToken();
        String refreshToken = response.getResponseBody().getRefreshToken();

        Cookie fingerprint = response.getResponseCookies().values().stream()
                .flatMap(List::stream)
//                .filter(cookie -> TokenUtils.FINGERPRINT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(el -> new Cookie(el.getName(), el.getValue()))
                .orElseThrow();

        return new TestAuth("Bearer " + accessToken, "Bearer " + refreshToken, fingerprint);
    }

    protected String extractURI(EntityExchangeResult<?> response) {
        return response.getResponseHeaders()
                .getLocation()
                .getPath()
                .replace(BaseTest.API_PREFIX, "");
    }
}
