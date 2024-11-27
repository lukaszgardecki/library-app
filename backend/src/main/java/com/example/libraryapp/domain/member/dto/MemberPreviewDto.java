package com.example.libraryapp.domain.member.dto;

import com.example.libraryapp.domain.member.Role;
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
