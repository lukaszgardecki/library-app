package com.example.libraryapp.OLDdomain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class MemberPreviewDto extends RepresentationModel<MemberPreviewDto> {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
}
