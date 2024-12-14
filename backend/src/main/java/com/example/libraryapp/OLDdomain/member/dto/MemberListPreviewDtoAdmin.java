package com.example.libraryapp.OLDdomain.member.dto;

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
