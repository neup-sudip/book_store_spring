package com.example.first.utils;

public class ResponseData {
    private Object data;

    private String message;

    private boolean result;
    public ResponseData() {
    }

    public ResponseData(Object data) {
        this.data = data;
    }

    public ResponseData(Object data, String message, boolean result) {
        this.data = data;
        this.message = message;
        this.result = result;
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
