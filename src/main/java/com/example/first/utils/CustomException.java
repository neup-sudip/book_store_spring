package com.example.first.utils;

import java.util.HashMap;
import java.util.Map;

public class CustomException extends RuntimeException {


    private Map<String, String> errors = new HashMap<>();

    public CustomException() {
    }

    public CustomException(Map errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
