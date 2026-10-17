package com.company.exception;

import com.company.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(CompanyNotFoundException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CompanyAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(CompanyAlreadyExistException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(UnauthorizedException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponseDto> handleAccessDenied(
            AccessDeniedException ex) {

        ApiErrorResponseDto error = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Access Denied"
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
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

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(CompanyPostNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(CompanyPostNotFoundException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CompanyLifeNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(CompanyLifeNotFoundException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
