package com.authService.exception;

import com.authService.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(UserNotFoundException ex) {


        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(UserAlreadyExistException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponseDto> hnadlerException(UnauthorizedException ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(),HttpStatus.UNAUTHORIZED.value(),ex.getMessage());
         return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
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


    @ExceptionHandler(OtpNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(OtpNotFoundException ex) {


        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }


    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ApiErrorResponseDto> handleInvalidOtp(
            InvalidOtpException ex) {

        ApiErrorResponseDto error = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ApiErrorResponseDto> handleOtpExpired(
            OtpExpiredException ex) {

        ApiErrorResponseDto error = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.GONE.value(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(error);
    }

    @ExceptionHandler(OtpAlreadyVerified.class)
    public ResponseEntity<ApiErrorResponseDto> handlerException(OtpAlreadyVerified ex){

        ApiErrorResponseDto error=new ApiErrorResponseDto(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
