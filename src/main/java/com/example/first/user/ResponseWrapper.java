package com.example.first.user;

public class ResponseWrapper {

    private Object resBody;

    private int status;

    private String message;

    public ResponseWrapper(Object resBody, int status, String message) {
        this.resBody = resBody;
        this.status = status;
        this.message = message;
    }

    public Object getResBody() {
        return resBody;
    }

    public void setResBody(Object resBody) {
        this.resBody = resBody;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
