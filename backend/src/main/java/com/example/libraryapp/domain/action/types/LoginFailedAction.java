package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("LOGIN_FAILED")
public class LoginFailedAction extends Action {
    public LoginFailedAction(Member member) {
        super(member.getId());
        this.message = Message.ACTION_LOGIN_FAILED.formatted(
                member.getPerson().getFirstName(),
                member.getPerson().getLastName(),
                member.getCard().getBarcode()
        );
    }
}
