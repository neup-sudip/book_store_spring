package com.example.first.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseWrapper extends ResponseEntity {

    private ResponseData data;

    private  int status;

    public ResponseWrapper(ResponseData data, int status) {
        super(data, HttpStatusCode.valueOf(status));
        this.data = data;
        this.status = status;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
