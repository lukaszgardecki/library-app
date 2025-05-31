package com.example.apigateway.infrastructure.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VersionControllerTest {

    @InjectMocks
    private VersionController versionController;

    private static final String TEST_VERSION = "1.0.0-TEST";

    @BeforeEach
    void setUp() {
        try {
            java.lang.reflect.Field versionField = VersionController.class.getDeclaredField("version");
            versionField.setAccessible(true);
            versionField.set(versionController, TEST_VERSION);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetAppVersion() {
        ResponseEntity<String> responseEntity = versionController.getAppVersion();
        assertNotNull(responseEntity, "Response entity should not be null");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "HTTP Status should be OK");
        assertEquals(TEST_VERSION, responseEntity.getBody(), "Response body should contain the test version");
    }
}