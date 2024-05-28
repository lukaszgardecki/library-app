package com.example.libraryapp.domain.member;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.config.assembler.UserModelAssembler;
import com.example.libraryapp.domain.exception.member.MemberHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.payment.UnsettledFineException;
import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.domain.lending.LendingRepository;
import com.example.libraryapp.domain.lending.LendingStatus;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
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
    private final PagedResourcesAssembler<Member> pagedResourcesAssembler;

    public PagedModel<MemberDto> findAllUsers(Pageable pageable) {
        Page<Member> userDtoPage =
                pageable.isUnpaged() ? new PageImpl<>(memberRepository.findAll()) : memberRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(userDtoPage, userModelAssembler);
    }

    public MemberDto findMemberById(Long id) {
        Member member = findMember(id);
        return userModelAssembler.toModel(member);
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
    public void deleteUserById(Long id) {
        Member member = findMember(id);
        checkIfUserHasReturnedAllBooks(member);
        checkIfUserHasAnyCharges(member);
        updateBookItemsStatus(member);
        cancelAllReservations(member);
        memberRepository.deleteById(id);
    }

    // TODO: 30.11.2023 czy te metody są potrzebne?
    // TODO: 30.11.2023 nie da się inaczej sprawdzić czy user ma rolę admin?

    private Member findMember(Long id) {
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
