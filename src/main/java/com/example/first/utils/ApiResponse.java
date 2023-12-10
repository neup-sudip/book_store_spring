package com.example.first.utils;

public class ApiResponse {
    private Object data;
    private String message;
    private boolean result;

    public ApiResponse(boolean result, Object data, String message) {
        this.data = data;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(){

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
