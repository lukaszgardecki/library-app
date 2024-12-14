package com.example.libraryapp.OLDdomain.member.mapper;

import com.example.libraryapp.OLDdomain.member.Member;
import com.example.libraryapp.OLDdomain.member.dto.MemberDtoAdmin;

public class MemberDtoAdminMapper {

    public static MemberDtoAdmin map(Member member) {
        MemberDtoAdmin dto = new MemberDtoAdmin();
        dto.setId(member.getId());
        dto.setFirstName(member.getPerson().getFirstName());
        dto.setLastName(member.getPerson().getLastName());
        dto.setGender(member.getPerson().getGender());
        dto.setStreetAddress(member.getPerson().getAddress().getStreetAddress());
        dto.setZipCode(member.getPerson().getAddress().getZipCode());
        dto.setCity(member.getPerson().getAddress().getCity());
        dto.setState(member.getPerson().getAddress().getState());
        dto.setCountry(member.getPerson().getAddress().getCountry());
        dto.setDateOfBirth(member.getPerson().getDateOfBirth());
        dto.setEmail(member.getEmail());
        dto.setPhoneNumber(member.getPerson().getPhone());
        dto.setPesel(member.getPerson().getPesel());
        dto.setNationality(member.getPerson().getNationality());
        dto.setMothersName(member.getPerson().getMothersName());
        dto.setFathersName(member.getPerson().getFathersName());
        dto.setCard(member.getCard());
        dto.setDateOfMembership(member.getDateOfMembership());
        dto.setTotalBooksBorrowed(member.getTotalBooksBorrowed());
        dto.setTotalBooksReserved(member.getTotalBooksReserved());
        dto.setCharge(member.getCharge());
        dto.setStatus(member.getStatus());
        dto.setLoanedItemsIds(member.getLoanedItemsIds());
        dto.setReservedItemsIds(member.getReservedItemsIds());
        dto.setRole(member.getRole());
        return dto;
    }
}
