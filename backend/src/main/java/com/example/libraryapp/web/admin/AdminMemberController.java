package com.example.libraryapp.web.admin;

import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberDtoAdmin;
import com.example.libraryapp.domain.member.dto.MemberUpdateAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.domain.member.Role.ADMIN;

@RestController
@RequestMapping("/api/v1/admin/members")
@RoleAuthorization(ADMIN)
@RequiredArgsConstructor
public class AdminMemberController {
    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        MemberDtoAdmin member = memberService.findMemberByIdAdmin(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody MemberUpdateAdminDto user) {
        MemberDto memberDto = memberService.updateMemberByAdmin(id, user);
        return ResponseEntity.ok(memberDto);
    }
}
