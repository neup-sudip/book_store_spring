package com.example.first.utils;

public class CustomException extends RuntimeException {
    private int status;

    public CustomException(String error) {
        super(error);
    }

    public CustomException(String error, int status) {
        super(error);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
