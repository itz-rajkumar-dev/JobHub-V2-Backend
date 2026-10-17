package com.category.exception;

import com.category.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(CategoryNotFoundException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(CategoryAlreadyExistException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(UnAuthorizedException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponseDto> handleAccessDenied(AccessDeniedException ex) {

        ApiErrorResponseDto error = new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), "Access Denied");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        ApiErrorResponseDto error = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
