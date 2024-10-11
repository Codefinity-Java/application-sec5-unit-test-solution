package com.codefinity.firstrestapibookssec4.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookResponseDTO {
    private String id;
    private String name;
    private String author;
    private String price;
}
