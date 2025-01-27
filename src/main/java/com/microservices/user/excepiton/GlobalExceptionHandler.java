package com.microservices.user.excepiton;


import com.microservices.user.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDto> resourceNotFound(
            ResourceNotFound r,
            WebRequest request
    ){
        ErrorDto errorDto = new ErrorDto(r.getMessage(),new Date(), request.getDescription(false));

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> globalException(
            Exception e,
            WebRequest request
    ){
        ErrorDto errorDto = new ErrorDto(e.getMessage(), new Date(), request.getDescription(false));

        return new ResponseEntity<>(errorDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
