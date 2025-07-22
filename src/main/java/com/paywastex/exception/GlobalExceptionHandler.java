package com.paywastex.exception;

import com.paywastex.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(LocalDateTime.now(), ex.getMessage(), null, 409));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(LocalDateTime.now(), ex.getMessage(), null, 404));
    }

    // Add more handlers as needed...

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> badCreds() {
        return build(HttpStatus.UNAUTHORIZED,"Invalid credentials");
    }

    @ExceptionHandler({LockedException.class, TooManyRequestsException.class})
    public ResponseEntity<ApiResponse<Void>> locked(RuntimeException ex) {
        return build(HttpStatus.LOCKED, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> unauth(RuntimeException ex){
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> generic(Exception ex){
        log.error("Unhandled",ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,"Server error");
    }

    private <T> ResponseEntity<ApiResponse<T>> build(HttpStatus s, String m){
        return ResponseEntity.status(s)
                .body(new ApiResponse<>(Instant.now(),m,null,s.value()));
    }
}