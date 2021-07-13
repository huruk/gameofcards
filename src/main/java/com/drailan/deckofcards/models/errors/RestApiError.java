package com.drailan.deckofcards.models.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestApiError {
    private String source;
    private String errorCode;
    private String message;
}
