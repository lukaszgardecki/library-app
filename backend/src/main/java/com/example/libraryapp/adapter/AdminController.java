package com.example.libraryapp.adapter;

import com.example.libraryapp.application.statistics.StatisticsFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.statistics.dto.StatisticsDto;
import com.example.libraryapp.domain.user.dto.AdminUserDetailsDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateAdminDto;
import com.example.libraryapp.infrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.domain.user.model.Role.ADMIN;

@RestController
@RequestMapping("/api/v1/admin/users")
@RoleAuthorization(ADMIN)
@RequiredArgsConstructor
class AdminController {
    private final StatisticsFacade statisticsFacade;
    private final UserFacade userFacade;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        AdminUserDetailsDto userAdminInfo = userFacade.getUserAdminInfo(id);
        return ResponseEntity.ok(userAdminInfo);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatisticsDto> getUsersStats() {
        StatisticsDto statistics = statisticsFacade.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateAdminDto userData) {
        UserDto user = userFacade.updateUserByAdmin(id, userData);
        return ResponseEntity.ok(user);
    }
}
