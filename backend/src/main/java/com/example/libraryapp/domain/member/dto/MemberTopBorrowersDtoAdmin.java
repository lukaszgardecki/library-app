package com.example.libraryapp.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberTopBorrowersDtoAdmin {
    private Long id;
    private int place;
    private String fullName;
    private int totalBooksBorrowed;
}
