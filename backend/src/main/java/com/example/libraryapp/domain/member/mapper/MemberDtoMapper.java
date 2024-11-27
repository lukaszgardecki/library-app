package com.example.libraryapp.domain.member.mapper;

import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.dto.MemberDto;

public class MemberDtoMapper {

    public static MemberDto map(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setFirstName(member.getPerson().getFirstName());
        dto.setLastName(member.getPerson().getLastName());
        dto.setGender(member.getPerson().getGender());
        dto.setAddress(
                "%s, %s %s, %s, %s".formatted(
                        member.getPerson().getAddress().getStreetAddress(),
                        member.getPerson().getAddress().getZipCode(),
                        member.getPerson().getAddress().getCity(),
                        member.getPerson().getAddress().getState(),
                        member.getPerson().getAddress().getCountry()
                )
        );
        dto.setDateOfBirth(member.getPerson().getDateOfBirth());
        dto.setEmail(member.getEmail());
        dto.setPhoneNumber(member.getPerson().getPhone());
        dto.setPesel(member.getPerson().getPesel());
        dto.setNationality(member.getPerson().getNationality());
        dto.setParentsNames(
                "%s, %s".formatted(
                        member.getPerson().getMothersName(),
                        member.getPerson().getFathersName()
                )
        );
        dto.setCard(member.getCard());
        dto.setDateOfMembership(member.getDateOfMembership());
        dto.setTotalBooksBorrowed(member.getTotalBooksBorrowed());
        dto.setTotalBooksReserved(member.getTotalBooksReserved());
        dto.setCharge(member.getCharge());
        dto.setStatus(member.getStatus());
        dto.setLoanedItemsIds(member.getLoanedItemsIds());
        dto.setReservedItemsIds(member.getReservedItemsIds());
        return dto;
    }
}
