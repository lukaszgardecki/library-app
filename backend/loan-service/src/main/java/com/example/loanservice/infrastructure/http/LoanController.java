package com.example.loanservice.infrastructure.http;

import com.example.loanservice.core.BookItemLoanFacade;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanId;
import com.example.loanservice.domain.model.values.LoanStatus;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.infrastructure.http.dto.BookItemLoanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
class LoanController {
    private final BookItemLoanFacade bookItemLoanFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<Page<BookItemLoanDto>> getAllBookLoans(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(required = false) Boolean renewable,
            Pageable pageable
    ) {
        Page<BookItemLoanDto> page = bookItemLoanFacade
                .getPageOfBookLoansByParams(new UserId(userId), status, renewable, pageable)
                .map(BookItemLoanMapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/all/list")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<List<BookItemLoanDto>> getAllLoansByUserId(
            @RequestParam("user_id") Long userId
    ) {
        List<BookItemLoanDto> list = bookItemLoanFacade.getAllLoansByUserId(new UserId(userId)).stream()
                .map(BookItemLoanMapper::toDto)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<Page<BookItemLoanDto>> getCurrentLoansByUserId(
            @RequestParam("user_id") Long userId,
            Pageable pageable
    ) {
        Page<BookItemLoanDto> page = bookItemLoanFacade.getCurrentLoansByUserId(new UserId(userId), pageable)
                .map(BookItemLoanMapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/current/list")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<List<BookItemLoanDto>> getCurrentLoansByUserId(
            @RequestParam("user_id") Long userId
    ) {
        List<BookItemLoanDto> list = bookItemLoanFacade.getCurrentLoansByUserId(new UserId(userId)).stream()
                .map(BookItemLoanMapper::toDto)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    ResponseEntity<BookItemLoanDto> getBookLoanById(@PathVariable Long id) {
        BookItemLoanDto loan = BookItemLoanMapper.toDto(bookItemLoanFacade.getBookLoan(new LoanId(id)));
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<BookItemLoanDto> borrowABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemLoanDto savedBookItemLoan =
                BookItemLoanMapper.toDto(
                        bookItemLoanFacade.borrowBookItem(new BookItemId(bookItemId), new UserId(userId))
                );
        URI savedCheckoutUri = createURI(savedBookItemLoan.id());
        return ResponseEntity.created(savedCheckoutUri).body(savedBookItemLoan);
    }

    @PostMapping("/renew")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
   ResponseEntity<BookItemLoanDto> renewABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemLoanDto renewedLoan = BookItemLoanMapper.toDto(
                bookItemLoanFacade.renewBookItemLoan(new BookItemId(bookItemId), new UserId(userId))
        );
        return ResponseEntity.ok(renewedLoan);
    }

    @PatchMapping("/return")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> returnABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        bookItemLoanFacade.returnBookItem(new BookItemId(bookItemId), new UserId(userId));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/lost")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> processLostBookItem(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        bookItemLoanFacade.processLostBookItem(new BookItemId(bookItemId), new UserId(userId));
        return ResponseEntity.ok().build();
    }

    private URI createURI(Long sourceId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sourceId)
                .toUri();
    }
}
