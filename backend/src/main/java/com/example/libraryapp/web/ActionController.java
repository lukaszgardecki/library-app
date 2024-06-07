package com.example.libraryapp.web;

import com.example.libraryapp.domain.action.ActionDto;
import com.example.libraryapp.domain.action.ActionService;
import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.config.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.domain.member.Role.ADMIN;
import static com.example.libraryapp.domain.member.Role.USER;

@RestController
@RequestMapping("/api/v1/actions")
@RequiredArgsConstructor
public class ActionController {
    private final ActionService actionService;
    private final AuthenticationService authService;

    @GetMapping
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<PagedModel<ActionDto>> getAllActions(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) String type,
            Pageable pageable
    ) {
        authService.checkIfAdminOrDataOwnerRequested(memberId);
        PagedModel<ActionDto> collectionModel = actionService.findActions(memberId, type, pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<ActionDto> getActionById(@PathVariable Long id) {
        ActionDto action = actionService.findActionById(id);
        authService.checkIfAdminOrDataOwnerRequested(action.getMemberId());
        return ResponseEntity.ok(action);
    }
}
