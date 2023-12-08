package com.example.libraryapp.web;

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

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedModel<MemberDto>> getAllUsers(Pageable pageable) {
        PagedModel<MemberDto> collectionModel = memberService.findAllUsers(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        memberService.checkIfAdminOrDataOwnerRequested(id);
        MemberDto member = memberService.findMemberById(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody MemberUpdateDto user) {
        memberService.checkIfAdminOrDataOwnerRequested(id);
        MemberDto memberDto = memberService.updateMember(id, user);
        return ResponseEntity.ok(memberDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        memberService.checkIfAdminOrDataOwnerRequested(id);
        memberService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
