package com.example.libraryapp.domain.member.dto;

import com.example.libraryapp.domain.member.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
public class MemberListPreviewDtoAdmin extends RepresentationModel<MemberListPreviewDtoAdmin> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfMembership;
    private AccountStatus status;
}
