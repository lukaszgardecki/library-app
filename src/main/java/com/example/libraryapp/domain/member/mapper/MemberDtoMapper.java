package com.example.libraryapp.domain.member.mapper;

import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.dto.MemberDto;

public class MemberDtoMapper {

    public static MemberDto map(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setFirstName(member.getPerson().getFirstName());
        dto.setLastName(member.getPerson().getLastName());
        dto.setEmail(member.getEmail());
        dto.setCard(member.getCard());
        return dto;
    }
}
