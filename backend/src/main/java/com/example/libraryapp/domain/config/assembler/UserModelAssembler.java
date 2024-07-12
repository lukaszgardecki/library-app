package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.mapper.MemberDtoMapper;
import com.example.libraryapp.web.LendingController;
import com.example.libraryapp.web.ReservationController;
import com.example.libraryapp.web.MemberController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class UserModelAssembler extends RepresentationModelAssemblerSupport<Member, MemberDto> {

    public UserModelAssembler() {
        super(MemberController.class, MemberDto.class);
    }

    @Override
    @NonNull
    public MemberDto toModel(@NonNull Member member) {
        MemberDto memberDto = MemberDtoMapper.map(member);
        memberDto.add(linkTo(methodOn(MemberController.class).getUserById(member.getId())).withSelfRel());
        memberDto.add(linkTo(methodOn(MemberController.class).getAllUsers(null, null)).withRel(IanaLinkRelations.COLLECTION));
        memberDto.add(linkTo(methodOn(LendingController.class).getAllLendings(member.getId(),null, null, null)).withRel("checkouts"));
        memberDto.add(linkTo(methodOn(ReservationController.class).getAllReservations(member.getId(), null, null)).withRel("reservations"));
        return memberDto;
    }

    @Override
    @NonNull
    public CollectionModel<MemberDto> toCollectionModel(@NonNull Iterable<? extends Member> entities) {
        CollectionModel<MemberDto> entityModel = super.toCollectionModel(entities);
        entityModel.add(linkTo(methodOn(MemberController.class).getAllUsers(null, null)).withSelfRel());
        return entityModel;
    }
}
