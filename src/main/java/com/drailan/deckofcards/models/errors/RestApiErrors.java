package com.drailan.deckofcards.models.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestApiErrors {
    private List<RestApiError> errors;
}
