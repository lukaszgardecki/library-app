package com.example.apigateway.infrastructure.http;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
class VersionController {

    @Value("${app.version:UNKNOWN}")
    private String version;

    @GetMapping
    ResponseEntity<String> getAppVersion() {
        return ResponseEntity.ok(version);
    }
}
