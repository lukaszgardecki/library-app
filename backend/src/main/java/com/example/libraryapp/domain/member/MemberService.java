package com.example.libraryapp.domain.member;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.exception.member.MemberHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.payment.UnsettledFineException;
import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.domain.lending.LendingRepository;
import com.example.libraryapp.domain.lending.LendingStatus;
import com.example.libraryapp.domain.member.assembler.UserModelAssembler;
import com.example.libraryapp.domain.member.assembler.UserModelAssemblerAdmin;
import com.example.libraryapp.domain.member.assembler.UserPreviewModelAssembler;
import com.example.libraryapp.domain.member.assembler.UserPreviewModelAssemblerAdmin;
import com.example.libraryapp.domain.member.dto.*;
import com.example.libraryapp.domain.reservation.Reservation;
import com.example.libraryapp.domain.reservation.ReservationRepository;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LendingRepository lendingRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserModelAssembler userModelAssembler;
    private final UserModelAssemblerAdmin userModelAssemblerAdmin;
    private final UserPreviewModelAssembler userPreviewModelAssembler;
    private final UserPreviewModelAssemblerAdmin userPreviewModelAssemblerAdmin;
    private final PagedResourcesAssembler<Member> pagedResourcesAssembler;

    public PagedModel<MemberListPreviewDtoAdmin> findAllUsers(String usersToSearch, Pageable pageable) {
        List<Member> users = memberRepository.findAllByString(usersToSearch);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), users.size());
        List<Member> paginatedList = users.subList(start, end);
        Page<Member> page = new PageImpl<>(paginatedList, pageable, users.size());
        return pagedResourcesAssembler.toModel(page, userPreviewModelAssemblerAdmin);
    }

    public MemberDto findMemberById(Long id) {
        Member member = findMember(id);
        return userModelAssembler.toModel(member);
    }

    public MemberPreviewDto findMemberPreviewById(Long id) {
        Member member = findMember(id);
        return userPreviewModelAssembler.toModel(member);
    }

    public MemberDtoAdmin findMemberByIdAdmin(Long id) {
        Member member = findMember(id);
        return userModelAssemblerAdmin.toModel(member);
    }

    @Transactional
    public MemberDto updateMember(Long id, MemberUpdateDto memberData) {
        Member memberToUpdate = findMember(id);
        Person person = memberToUpdate.getPerson();
        Address address = person.getAddress();

        if (memberData.getStreetAddress() != null) address.setStreetAddress(memberData.getStreetAddress());
        if (memberData.getZipCode() != null) address.setZipCode(memberData.getZipCode());
        if (memberData.getCity() != null) address.setCity(memberData.getCity());
        if (memberData.getState() != null) address.setState(memberData.getState());
        if (memberData.getCountry() != null) address.setCountry(memberData.getCountry());

        if (memberData.getFirstName() != null) person.setFirstName(memberData.getFirstName());
        if (memberData.getLastName() != null) person.setLastName(memberData.getLastName());
        if (memberData.getPhone() != null) person.setPhone(memberData.getPhone());

        // TODO: 05.06.2023 Email must be unique!
        if (memberData.getEmail() != null) memberToUpdate.setEmail(memberData.getEmail());
        if (memberData.getPassword() != null) {
            memberToUpdate.setPassword(passwordEncoder.encode(memberData.getPassword()));
        }
        return userModelAssembler.toModel(memberToUpdate);
    }

    @Transactional
    public MemberDto updateMemberByAdmin(Long id, MemberUpdateAdminDto memberData) {
        Member memberToUpdate = findMember(id);
        Person person = memberToUpdate.getPerson();
        Address address = person.getAddress();

        if (memberData.getFirstName() != null) person.setFirstName(memberData.getFirstName());
        if (memberData.getLastName() != null) person.setLastName(memberData.getLastName());
        // TODO: 05.06.2023 Email must be unique!
        if (memberData.getEmail() != null) memberToUpdate.setEmail(memberData.getEmail());

        if (memberData.getStreetAddress() != null) address.setStreetAddress(memberData.getStreetAddress());
        if (memberData.getZipCode() != null) address.setZipCode(memberData.getZipCode());
        if (memberData.getCity() != null) address.setCity(memberData.getCity());
        if (memberData.getState() != null) address.setState(memberData.getState());
        if (memberData.getCountry() != null) address.setCountry(memberData.getCountry());

        if (memberData.getPhone() != null) person.setPhone(memberData.getPhone());

        if (memberData.getGender() != null) person.setGender(memberData.getGender());
        if (memberData.getPesel() != null) person.setPesel(memberData.getPesel());
        if (memberData.getDateOfBirth() != null) person.setDateOfBirth(memberData.getDateOfBirth());
        if (memberData.getNationality() != null) person.setNationality(memberData.getNationality());
        if (memberData.getMothersName() != null) person.setMothersName(memberData.getMothersName());
        if (memberData.getFathersName() != null) person.setFathersName(memberData.getFathersName());

        if (memberData.getAccountStatus() != null) memberToUpdate.setStatus(memberData.getAccountStatus());
        if (memberData.getCardStatus() != null) memberToUpdate.getCard().setStatus(memberData.getCardStatus());
        if (memberData.getRole() != null) memberToUpdate.setRole(memberData.getRole());

        return userModelAssembler.toModel(memberToUpdate);
    }

    @Transactional
    public void deleteUserById(Long id) {
        Member member = findMember(id);
        checkIfUserHasReturnedAllBooks(member);
        checkIfUserHasAnyCharges(member);
        updateBookItemsStatus(member);
        cancelAllReservations(member);
        deleteItemsIds(member);
        memberRepository.deleteById(id);
    }

    // TODO: 30.11.2023 czy te metody są potrzebne?
    // TODO: 30.11.2023 nie da się inaczej sprawdzić czy user ma rolę admin?

    Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    private void checkIfUserHasReturnedAllBooks(Member member) {
        Optional<Lending> currentLendings = lendingRepository.findAllByMemberId(member.getId())
                .stream()
                .filter(len -> len.getStatus() == LendingStatus.CURRENT)
                .findAny();
        if (currentLendings.isPresent()) throw new MemberHasNotReturnedBooksException();
    }

    private void checkIfUserHasAnyCharges(Member member) {
        if (member.hasCharges()) throw new UnsettledFineException();
    }

    private void cancelAllReservations(Member member) {
        reservationRepository.findAllByMemberId(member.getId())
                .stream()
                .filter(res -> res.getStatus() == ReservationStatus.READY || res.getStatus() == ReservationStatus.PENDING)
                .forEach(Reservation::updateAfterCancelling);
    }

    private void deleteItemsIds(Member member) {
        member.setLoanedItemsIds(Collections.emptyList());
        member.setReservedItemsIds(Collections.emptyList());
    }

    private void updateBookItemsStatus(Member member) {
        reservationRepository.findAllCurrentReservationsByMemberId(member.getId())
                .forEach(reservation ->  {
                    BookItem bookItem = reservation.getBookItem();
                    if (bookItem.getStatus() != BookItemStatus.LOANED) {
                        boolean isBookReserved =
                                reservationRepository.findAllCurrentReservationsByBookItemId(bookItem.getId())
                                        .stream()
                                        .anyMatch(res -> !Objects.equals(res.getMember().getId(), member.getId()));
                        bookItem.updateAfterReservationCancelling(isBookReserved);
                    }
                });
    }
}
