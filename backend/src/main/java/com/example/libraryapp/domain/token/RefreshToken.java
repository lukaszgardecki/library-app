package com.example.libraryapp.domain.token;

import com.example.libraryapp.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RefreshToken extends Token {

    public RefreshToken(Member member, String token) {
        super.member = member;
        super.token = token;
        super.tokenType = TokenType.BEARER;
        super.expired = false;
        super.revoked = false;
    }
}
