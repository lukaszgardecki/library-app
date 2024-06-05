package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REGISTER")
public class RegisterAction extends Action {
    public RegisterAction(Member member) {
        super(member.getId());
        this.message = Message.ACTION_REGISTER.formatted(
                member.getPerson().getFirstName(),
                member.getPerson().getLastName(),
                member.getCard().getBarcode()
        );
    }
}
