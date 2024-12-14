package com.example.libraryapp.OLDdomain.member.mapper;

import com.example.libraryapp.OLDdomain.member.Member;
import com.example.libraryapp.OLDdomain.member.dto.MemberPreviewDto;

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
