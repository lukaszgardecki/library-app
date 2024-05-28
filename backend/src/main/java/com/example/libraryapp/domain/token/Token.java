package com.example.libraryapp.domain.token;

import com.example.libraryapp.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String token;

    @Enumerated(EnumType.STRING)
    protected TokenType tokenType;

    protected boolean expired;
    protected boolean revoked;

    @ManyToOne
    @JoinColumn(name = "member_id")
    protected Member member;

    public void setInvalid() {
        this.expired = true;
        this.revoked = true;
    }
}
