package com.example.userservice.infrastructure.http;

import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.core.user.UserFacade;
import com.example.userservice.domain.dto.person.PersonDto;
import com.example.userservice.domain.dto.user.UserDto;
import com.example.userservice.domain.dto.user.UserListPreviewDto;
import com.example.userservice.domain.dto.user.UserPreviewDto;
import com.example.userservice.domain.dto.user.UserUpdateDto;
import com.example.userservice.domain.model.person.PersonId;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
class UserController {
    private final UserFacade userFacade;
    private final PersonFacade personFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> page = userFacade.getAllUsers(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<UserListPreviewDto>> getAllUserListPreviews(
            @RequestParam(name = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<UserListPreviewDto> page = userFacade.getUserList(query, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userFacade.getUserById(new UserId(id));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/person")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<PersonDto> getPersonByUserId(@PathVariable Long userId) {
        UserDto user = userFacade.getUserById(new UserId(userId));
        PersonDto person = personFacade.getPersonById(new PersonId(user.getPersonId()));
        return ResponseEntity.ok(person);
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<UserDto> getUserDetailsById(@PathVariable Long id) {
        UserDto user = userFacade.getUserById(new UserId(id));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/preview")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<UserPreviewDto> getUserPreviewById(@PathVariable Long id) {
        UserPreviewDto userPreview = userFacade.getUserPreview(new UserId(id));
        return ResponseEntity.ok(userPreview);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        UserDto updatedUser = userFacade.updateUser(new UserId(id), user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userFacade.deleteUserById(new UserId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/verify/loan")
    ResponseEntity<Void> verifyUserForBookItemLoan(@PathVariable Long userId) {
        userFacade.verifyUserForBookItemLoan(new UserId(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/verify/renewal")
    ResponseEntity<Void> verifyUserForBookItemRenewal(@PathVariable Long userId) {
        userFacade.verifyUserForBookItemRenewal(new UserId(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/verify/request")
    ResponseEntity<Void> verifyUserForBookItemRequest(@PathVariable Long userId) {
        userFacade.verifyUserForBookItemRequest(new UserId(userId));
        return ResponseEntity.noContent().build();
    }
}
