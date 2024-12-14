package com.example.libraryapp.OLDdomain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberTopBorrowersDtoAdmin {
    private Long id;
    private int rank;
    private String fullName;
    private int totalBooksBorrowed;
}
