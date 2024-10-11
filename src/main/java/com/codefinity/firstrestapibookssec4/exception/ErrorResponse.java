package com.codefinity.firstrestapibookssec4.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private String error;
}
