package com.example.libraryapp.OLDweb;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import com.example.libraryapp.OLDdomain.member.MemberService;
import com.example.libraryapp.OLDdomain.member.dto.MemberDto;
import com.example.libraryapp.OLDdomain.member.dto.MemberListPreviewDtoAdmin;
import com.example.libraryapp.OLDdomain.member.dto.MemberPreviewDto;
import com.example.libraryapp.OLDdomain.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.OLDdomain.member.Role.ADMIN;
import static com.example.libraryapp.OLDdomain.member.Role.USER;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
//    private final AuthenticationService authService;
    private final AuthenticationFacade authFacade;

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
        authFacade.validateOwnerOrAdminAccess(id);
        MemberDto member = memberService.findMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{id}/preview")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> getUserPreviewById(@PathVariable Long id) {
        authFacade.validateOwnerOrAdminAccess(id);
        MemberPreviewDto member = memberService.findMemberPreviewById(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody MemberUpdateDto user) {
        authFacade.validateOwnerOrAdminAccess(id);
        MemberDto memberDto = memberService.updateMember(id, user);
        return ResponseEntity.ok(memberDto);
    }

    @DeleteMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        authFacade.validateOwnerOrAdminAccess(id);
        memberService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
