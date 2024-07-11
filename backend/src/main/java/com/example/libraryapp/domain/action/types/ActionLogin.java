package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("LOGIN")
public class ActionLogin extends Action {
    public ActionLogin(MemberDto member) {
        super(member.getId());
        this.message = Message.ACTION_LOGIN_SUCCEEDED.getMessage(
                member.getFirstName(),
                member.getLastName(),
                member.getCard().getBarcode()
        );
    }
}
