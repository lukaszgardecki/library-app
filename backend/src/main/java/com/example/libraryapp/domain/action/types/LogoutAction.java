package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("LOGOUT")
public class LogoutAction extends Action {
    public LogoutAction(Member member) {
        super(member.getId());
        this.message = Message.ACTION_LOGOUT.formatted(
                member.getPerson().getFirstName(),
                member.getPerson().getLastName(),
                member.getCard().getBarcode()
        );
    }
}
