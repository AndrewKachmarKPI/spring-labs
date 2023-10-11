package com.spring.labs.lab4.exceptions;

import java.util.List;

public record ValidationErrorResponse(String field, List<String> errors) {
}
