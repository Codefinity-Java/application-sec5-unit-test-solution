package com.codefinity.firstrestapibookssec4.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookRequestDTO {
    private String name;
    private String author;
    private String price;
}
