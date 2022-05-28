package com.otto15.client.exceptions;

import java.util.List;

public class ValidationException extends Exception {
    private final List<String> validationErrorsList;

    public ValidationException(List<String> validationErrorsList) {
        this.validationErrorsList = validationErrorsList;
    }

    public List<String> getValidationErrorsList() {
        return validationErrorsList;
    }
}
