package com.example.libraryapp.OLDmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionRequest {
    private Long memberId;
    private String bookBarcode;
}
