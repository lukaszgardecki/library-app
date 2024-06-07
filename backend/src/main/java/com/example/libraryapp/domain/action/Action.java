package com.example.libraryapp.domain.action;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Long memberId;
    @Formula("type")
    protected String type;
    @Size(max = 1500)
    protected String message;
    protected LocalDateTime createdAt;

    protected Action(Long memberId) {
        this.memberId = memberId;
        this.createdAt = LocalDateTime.now();
    }
}
