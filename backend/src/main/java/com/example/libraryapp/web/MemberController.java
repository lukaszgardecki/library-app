package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberListPreviewDtoAdmin;
import com.example.libraryapp.domain.member.dto.MemberPreviewDto;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.domain.member.Role.ADMIN;
import static com.example.libraryapp.domain.member.Role.USER;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationService authService;

    @GetMapping
    @RoleAuthorization({ADMIN})
    public ResponseEntity<PagedModel<MemberListPreviewDtoAdmin>> getAllUsers(
            @RequestParam(name = "q", required = false)
            String usersToSearch,
            Pageable pageable
    ) {
        PagedModel<MemberListPreviewDtoAdmin> collectionModel = memberService.findAllUsers(usersToSearch, pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        authService.checkIfAdminOrDataOwnerRequested(id);
        MemberDto member = memberService.findMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{id}/preview")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> getUserPreviewById(@PathVariable Long id) {
        authService.checkIfAdminOrDataOwnerRequested(id);
        MemberPreviewDto member = memberService.findMemberPreviewById(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody MemberUpdateDto user) {
        authService.checkIfAdminOrDataOwnerRequested(id);
        MemberDto memberDto = memberService.updateMember(id, user);
        return ResponseEntity.ok(memberDto);
    }

    @DeleteMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        authService.checkIfAdminOrDataOwnerRequested(id);
        memberService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
