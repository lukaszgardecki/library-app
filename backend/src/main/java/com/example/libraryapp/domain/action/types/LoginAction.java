package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("LOGIN")
public class LoginAction extends Action {
    public LoginAction(Member member) {
        super(member.getId());
        this.message = Message.ACTION_LOGIN.formatted(
                member.getPerson().getFirstName(),
                member.getPerson().getLastName(),
                member.getCard().getBarcode()
        );
    }
}
