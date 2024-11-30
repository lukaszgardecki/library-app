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
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Page<Member> page = memberRepository.findAllByString(usersToSearch, pageable);
        return pagedResourcesAssembler.toModel(page, userPreviewModelAssemblerAdmin);
    }

    public MembersStatsAdminDto findAllUsersStats() {
        return MembersStatsAdminDto.builder()
                .todayLendings(lendingRepository.countLendingsToday())
                .activeUsersThisMonth(lendingRepository.countActiveMembersThisMonth())
                .newUsersThisMonth(memberRepository.countMembersRegisteredThisMonth())
                .usersCount(memberRepository.count())
                .favGenres(findTopGenres(5))
                .lendingsLastYearByMonth(countLendingsLastYearByMonth())
                .newLendingsLastWeekByDay(countLendingsLastWeekByDay(LendingStatus.CURRENT))
                .returnedLendingsLastWeekByDay(countLendingsLastWeekByDay(LendingStatus.COMPLETED))
                .topBorrowers(findTop10Borrowers())
                .build();
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
        MemberDtoAdmin memberDto = userModelAssemblerAdmin.toModel(member);
        List<Lending> lendings = lendingRepository.findAllByMemberId(id);
        memberDto.setLendingsPerMonth(countLendingsPerMonth(lendings));
        memberDto.setGenresStats(getTopGenres(lendings, 5));
        return memberDto;
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

    private List<Integer> countLendingsPerMonth(List<Lending> lendings) {
        List<Integer> lendingsPerMonth = new ArrayList<>();
        YearMonth currentMonth = YearMonth.now();

        Map<YearMonth, Long> lendingsCountMap = lendings.stream()
                .collect(Collectors.groupingBy(
                        lending -> YearMonth.from(lending.getCreationDate()),
                        Collectors.counting()
                ));

        for (int i = 11; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            int count = lendingsCountMap.getOrDefault(month, 0L).intValue();
            lendingsPerMonth.add(count);
        }
        return lendingsPerMonth;
    }

    private Map<String, Integer> getTopGenres(List<Lending> lendings, int count) {
        return lendings.stream()
                .collect(Collectors.groupingBy(
                        lending -> lending.getBookItem().getBook().getSubject(),
                        Collectors.summingInt(lending -> 1)
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(count)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Map<String, Long> findTopGenres(int count) {
        List<Object[]> results = lendingRepository.findTopSubjectsWithLendingCount(count);
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1],
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    private List<Long> countLendingsLastYearByMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(11).withDayOfMonth(1);
        List<Object[]> rawCounts = lendingRepository.countLendingsByMonth(startDate, now);

        Map<Integer, Long> monthCounts = rawCounts.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Long) row[1]
                ));

        return IntStream.rangeClosed(0, 11)
                .mapToObj(i -> now.minusMonths(11 - i).getMonthValue())
                .map(month -> monthCounts.getOrDefault(month, 0L))
                .collect(Collectors.toList());
    }

    private List<Long> countLendingsLastWeekByDay(LendingStatus status) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(6);
        List<Object[]> rawCounts = lendingRepository.countLendingsByDay(startDate, now, status);
        Map<Integer, Long> counts = rawCounts.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).intValue(),
                        row -> ((Number) row[1]).longValue()
                ));

        return IntStream.rangeClosed(1, 7)
                .mapToObj(i -> now.minusDays(7 - i).getDayOfWeek().getValue())
                .map(day -> counts.getOrDefault(day, 0L))
                .collect(Collectors.toList());
    }

    private List<MemberTopBorrowersDtoAdmin> findTop10Borrowers() {
        List<Member> top10Borrowers = memberRepository.findTop10Borrowers();
        return top10Borrowers.stream()
                .map(member -> MemberTopBorrowersDtoAdmin.builder()
                        .id(member.getId())
                        .place(top10Borrowers.indexOf(member) + 1)
                        .fullName(String.format("%s %s", member.getPerson().getFirstName(), member.getPerson().getLastName()))
                        .totalBooksBorrowed(member.getTotalBooksBorrowed())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
