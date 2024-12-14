package com.example.libraryapp.NEWadapter;

import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.user.dto.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class UserRegistrationController {
    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserDto request) {
        userFacade.registerNewUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fu")
    public ResponseEntity<Void> generateFakeUsers(@RequestParam int amount) {
        for (int i = 0; i < amount; i++) {
//            userFacade.registerNewUser(FakeUserGenerator.generate());
        }
        return ResponseEntity.ok().build();
    }
}
