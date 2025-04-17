package com.example.libraryapp.core.token;

import com.example.libraryapp.domain.user.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

class TokenClaimsBuilder {
    private final Map<String, Object> claims = new HashMap<>();

    public TokenClaimsBuilder addFingerprint(Fingerprint fingerprint) {
        claims.put(FingerprintGenerator.FINGERPRINT_NAME, fingerprint.hash());
        return this;
    }

    public TokenClaimsBuilder addUserDetails(UserDto user) {
        claims.put(TokenUtils.ID_CLAIM_NAME, user.getId());
        claims.put(TokenUtils.USER_ROLE, user.getRole().name());
        return this;
    }

    public Map<String, Object> build() {
        return new HashMap<>(claims);
    }
}
