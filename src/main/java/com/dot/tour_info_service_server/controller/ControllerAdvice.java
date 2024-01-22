package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.sql.SQLException;

@RestControllerAdvice
public class ControllerAdvice {
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handlerException(final IllegalArgumentException e,
//                                                          final HttpServletRequest request) {
//        return new ResponseEntity<>(
//                ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), request), HttpStatus.BAD_REQUEST);
//    }


    /*common error*/
    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorResponse> errorHandler(BindException e, final HttpServletRequest request) {

        return new ResponseEntity<>(
                ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getAllErrors().get(0).getDefaultMessage(), request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> errorHandler(IllegalArgumentException e, final HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<ErrorResponse> errorHandler(NullPointerException e, final HttpServletRequest request){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), request),
                HttpStatus.BAD_REQUEST);
    }

    /*Http error*/
    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<ErrorResponse> errorHandler(HttpClientErrorException e, final HttpServletRequest request){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage(), request),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {HttpServerErrorException .class})
    public ResponseEntity<ErrorResponse> errorHandler(HttpServerErrorException e, final HttpServletRequest request){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {UnknownHttpStatusCodeException.class})
    public ResponseEntity<ErrorResponse> errorHandler(UnknownHttpStatusCodeException e,
                                                      final HttpServletRequest request){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), request),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeExceptionHandle(RuntimeException e, final HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> dataExceptionHandle(DataAccessException e, final HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlExceptionHandle(SQLException e, final HttpServletRequest request){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
