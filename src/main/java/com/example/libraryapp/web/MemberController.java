package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedModel<MemberDto>> getAllUsers(Pageable pageable) {
        PagedModel<MemberDto> collectionModel = memberService.findAllUsers(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MemberDto> getUserById(@PathVariable Long id) {
        Optional<MemberDto> member = memberService.findMemberById(id);
        member.ifPresent(mem -> memberService.checkIfAdminOrDataOwnerRequested(mem.getId()));
        return member
                .map(ResponseEntity::ok)
                .orElseThrow(MemberNotFoundException::new);
    }

    @PatchMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody MemberUpdateDto user) {
        memberService.checkIfAdminOrDataOwnerRequested(id);
        return memberService.updateMember(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        memberService.checkIfAdminOrDataOwnerRequested(id);
        memberService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
