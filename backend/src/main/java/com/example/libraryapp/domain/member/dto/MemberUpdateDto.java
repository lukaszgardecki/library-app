package com.example.libraryapp.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto extends MemberUpdate {
    private String password;
}
