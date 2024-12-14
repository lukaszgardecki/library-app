package com.example.libraryapp.OLDdomain.member.mapper;

import com.example.libraryapp.OLDdomain.member.Member;
import com.example.libraryapp.OLDdomain.member.dto.MemberListPreviewDtoAdmin;

public class MemberListPreviewDtoMapper {

    public static MemberListPreviewDtoAdmin map(Member member) {
        MemberListPreviewDtoAdmin dto = new MemberListPreviewDtoAdmin();
        dto.setId(member.getId());
        dto.setFirstName(member.getPerson().getFirstName());
        dto.setLastName(member.getPerson().getLastName());
        dto.setEmail(member.getEmail());
        dto.setDateOfMembership(member.getDateOfMembership());
        dto.setStatus(member.getStatus());
        return dto;
    }
}
