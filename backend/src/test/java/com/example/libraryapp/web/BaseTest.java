package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class BaseTest {

    @Autowired
    protected WebTestClient client;

    @Autowired
    protected AuthenticationService authenticationService;

    protected String adminToken;
    protected String userToken;
    protected String user5Token;
    protected String warehouseToken;
    protected String cashierToken;

    @BeforeAll
    void authenticate() {
        this.adminToken = authenticate("admin@example.com", "adminpass");
        this.userToken = authenticate("user@example.com", "userpass");
        this.user5Token = authenticate("p.smerf@gmail.com", "userpass3");
        this.warehouseToken = authenticate("m.zul@gmail.com", "userpass5");
        this.cashierToken = authenticate("l.gaga@gmail.com", "userpass4");
    }

    private String authenticate(String username, String password) {
        LoginRequest user = new LoginRequest();
        user.setUsername(username);
        user.setPassword(password);
        LoginResponse loginResponse = authenticationService.authenticate(user);
        String token = loginResponse.getToken();
        return "Bearer " + token;
    }
}
