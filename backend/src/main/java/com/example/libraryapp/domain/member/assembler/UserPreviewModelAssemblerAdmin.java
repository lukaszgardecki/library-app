package com.example.libraryapp.domain.member.assembler;

import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.dto.MemberListPreviewDtoAdmin;
import com.example.libraryapp.domain.member.mapper.MemberListPreviewDtoMapper;
import com.example.libraryapp.web.LendingController;
import com.example.libraryapp.web.MemberController;
import com.example.libraryapp.web.ReservationController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class UserPreviewModelAssemblerAdmin extends RepresentationModelAssemblerSupport<Member, MemberListPreviewDtoAdmin> {

    public UserPreviewModelAssemblerAdmin() {
        super(MemberController.class, MemberListPreviewDtoAdmin.class);
    }

    @Override
    @NonNull
    public MemberListPreviewDtoAdmin toModel(@NonNull Member member) {
        MemberListPreviewDtoAdmin memberDto = MemberListPreviewDtoMapper.map(member);
        memberDto.add(linkTo(methodOn(MemberController.class).getUserById(member.getId())).withSelfRel());
        memberDto.add(linkTo(methodOn(MemberController.class).getAllUsers(null, null)).withRel(IanaLinkRelations.COLLECTION));
        memberDto.add(linkTo(methodOn(LendingController.class).getAllLendings(member.getId(),null, null, null)).withRel("checkouts"));
        memberDto.add(linkTo(methodOn(ReservationController.class).getAllReservations(member.getId(), null, null)).withRel("reservations"));
        return memberDto;
    }

    @Override
    @NonNull
    public CollectionModel<MemberListPreviewDtoAdmin> toCollectionModel(@NonNull Iterable<? extends Member> entities) {
        CollectionModel<MemberListPreviewDtoAdmin> entityModel = super.toCollectionModel(entities);
        entityModel.add(linkTo(methodOn(MemberController.class).getAllUsers(null, null)).withSelfRel());
        return entityModel;
    }
}
