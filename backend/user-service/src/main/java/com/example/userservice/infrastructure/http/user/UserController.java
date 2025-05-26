package com.example.userservice.infrastructure.http.user;

import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.core.user.UserFacade;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserUpdate;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.http.user.dto.*;
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
    private final DetailsAggregator detailsAggregator;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> page = userFacade.getAllUsers(pageable).map(UserMapper::toDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<UserListPreviewDto>> getAllUserListPreviews(
            @RequestParam(name = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<UserListPreviewDto> page = detailsAggregator.getUserList(query, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = UserMapper.toDto(userFacade.getUserById(new UserId(id)));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/person")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<PersonDto> getPersonByUserId(@PathVariable Long userId) {
        User user = userFacade.getUserById(new UserId(userId));
        PersonDto person = PersonMapper.toDto(personFacade.getPersonById(user.getPersonId()));
        return ResponseEntity.ok(person);
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable Long id) {
        UserDetailsDto userDetails = detailsAggregator.getUserDetails(new UserId(id));
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping("/{id}/preview")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<UserPreviewDto> getUserPreviewById(@PathVariable Long id) {
        UserPreviewDto userPreview = detailsAggregator.getUserPreview(new UserId(id));
        return ResponseEntity.ok(userPreview);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto body) {
        UserUpdate fieldsToUpdate = UserMapper.toModel(body);
        UserDto updatedUser = UserMapper.toDto(userFacade.updateUser(new UserId(id), fieldsToUpdate));
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
