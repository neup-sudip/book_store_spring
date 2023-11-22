package com.example.first.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseWrapper handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String errorTitle = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(errorTitle, errorMessage);
        });

        ResponseData res = new ResponseData(errors, "Error !", false);
        return new ResponseWrapper(res, 400);
    }

//    @ExceptionHandler(Exception.class)
//    public String handleError(MethodArgumentNotValidException exception, HttpServletRequest request, Model model){
//
//        Map<String, String> errors = new HashMap<>();
//
//        exception.getBindingResult().getAllErrors().forEach(error -> {
//            String errorTitle = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(errorTitle, errorMessage);
//        });
//
//        String url = request.getRequestURI();
//        model.addAttribute("errors", errors);
//
//        if(url.equals("/users/register")){
//            return "adduser";
//        } else if (url.contains("/users/edit/")) {
//            return "edituser";
//        }else{
//            return "login";
//        }
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseWrapper handleCustomException(CustomException exception){
        ResponseData res = new ResponseData(null, exception.getMessage(), false);
        return new ResponseWrapper(res, 400);
    }

//    @ExceptionHandler(CustomException.class)
//    public String handleCustomException(CustomException exception, HttpServletRequest request, Model model){
//        String url = request.getRequestURI();
//        model.addAttribute("errors", exception.getErrors());
//
//        if(url.equals("/users/register")){
//            return "adduser";
//        } else if (url.contains("/users/edit/")) {
//            return "edituser";
//        }else{
//            return "login";
//        }
//    }

}
