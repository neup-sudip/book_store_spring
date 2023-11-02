package com.example.first.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseWrapper extends ResponseEntity {

    public ResponseWrapper(ResponseData data, int status) {
        super(data, HttpStatusCode.valueOf(status));
    }
}
