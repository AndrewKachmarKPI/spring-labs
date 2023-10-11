package com.spring.labs.lab6.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ErrorResponse {
    private Integer code;
    private String error;
    private String message;
    private String path;
}
