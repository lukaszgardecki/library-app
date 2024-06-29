package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("LOGIN_FAILED")
public class LoginFailedAction extends Action {
    public LoginFailedAction(MemberDto member) {
        super(member.getId());
        this.message = Message.ACTION_LOGIN_FAILED.formatted(
                member.getFirstName(),
                member.getLastName(),
                member.getCard().getBarcode()
        );
    }
}
