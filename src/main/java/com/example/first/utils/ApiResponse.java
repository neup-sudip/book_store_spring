package com.example.first.utils;

public class ApiResponse {
    private Object data;
    private String message;
    private int status;
    private boolean result;

    public ApiResponse(boolean result, Object data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
