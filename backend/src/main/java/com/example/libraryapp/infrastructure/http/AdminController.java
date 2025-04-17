package com.example.libraryapp.infrastructure.http;

import com.example.libraryapp.core.statistics.StatisticsFacade;
import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.statistics.dto.StatisticsDto;
import com.example.libraryapp.domain.user.dto.UserDetailsAdminDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateAdminDto;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
class AdminController {
    private final StatisticsFacade statisticsFacade;
    private final UserFacade userFacade;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDetailsAdminDto userAdminInfo = userFacade.getUserDetailsAdmin(new UserId(id));
        return ResponseEntity.ok(userAdminInfo);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatisticsDto> getUsersStats() {
        StatisticsDto statistics = statisticsFacade.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateAdminDto userData) {
        UserDto user = userFacade.updateUserByAdmin(new UserId(id), userData);
        return ResponseEntity.ok(user);
    }
}
