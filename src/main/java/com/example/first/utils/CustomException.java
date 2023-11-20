package com.example.first.utils;

import java.util.HashMap;
import java.util.Map;

public class CustomException extends RuntimeException {
    public CustomException(String error) {
        super(error);
    }
}
