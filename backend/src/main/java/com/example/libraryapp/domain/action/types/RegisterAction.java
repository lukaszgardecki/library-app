package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REGISTER")
public class RegisterAction extends Action {
    public RegisterAction(MemberDto member) {
        super(member.getId());
        this.message = Message.ACTION_REGISTER.formatted(
                member.getFirstName(),
                member.getLastName(),
                member.getCard().getBarcode()
        );
    }
}
