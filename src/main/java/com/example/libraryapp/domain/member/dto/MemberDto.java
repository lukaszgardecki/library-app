package com.example.libraryapp.domain.member.dto;

import com.example.libraryapp.domain.card.LibraryCard;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class MemberDto extends RepresentationModel<MemberDto> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LibraryCard card;
}