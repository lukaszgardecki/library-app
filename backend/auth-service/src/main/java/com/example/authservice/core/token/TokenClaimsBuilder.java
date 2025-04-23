package com.example.authservice.core.token;

import com.example.authservice.domain.dto.auth.UserAuthDto;

import java.util.HashMap;
import java.util.Map;

class TokenClaimsBuilder {
    private final Map<String, Object> claims = new HashMap<>();

    public TokenClaimsBuilder addFingerprint(Fingerprint fingerprint) {
        claims.put(FingerprintGenerator.FINGERPRINT_NAME, fingerprint.hash());
        return this;
    }

    public TokenClaimsBuilder addUserDetails(UserAuthDto userAuth) {
        claims.put(TokenUtils.ID_CLAIM_NAME, userAuth.userId());
        claims.put(TokenUtils.USER_ROLE, userAuth.role().name());
        return this;
    }

    public Map<String, Object> build() {
        return new HashMap<>(claims);
    }
}
