package com.example.libraryapp.NEWinfrastructure.persistence.jpa.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UserActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserActivityType type;

    @Size(max = 1500)
    private String message;
    private LocalDateTime createdAt;
}
