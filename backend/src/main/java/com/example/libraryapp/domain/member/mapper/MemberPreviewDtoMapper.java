package com.example.libraryapp.domain.member.mapper;

import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.dto.MemberPreviewDto;

public class MemberPreviewDtoMapper {

    public static MemberPreviewDto map(Member member) {
        MemberPreviewDto dto = new MemberPreviewDto();
        dto.setId(member.getId());
        dto.setFirstName(member.getPerson().getFirstName());
        dto.setLastName(member.getPerson().getLastName());
        dto.setRole(member.getRole());
        return dto;
    }
}
