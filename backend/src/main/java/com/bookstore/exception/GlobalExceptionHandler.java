package com.bookstore.exception;
import com.bookstore.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice @Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(BookNotFoundException ex, HttpServletRequest req) {
        log.error("Not found: {}", ex.getMessage());
        ErrorResponse err = ErrorResponse.builder()
            .timestamp(java.time.LocalDateTime.now())
            .status(404).error("NOT_FOUND").message(ex.getMessage()).path(req.getRequestURI()).build();
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InsufficientStockException.class, CartEmptyException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, HttpServletRequest req) {
        ErrorResponse err = ErrorResponse.builder()
            .timestamp(java.time.LocalDateTime.now())
            .status(400).error("BAD_REQUEST").message(ex.getMessage()).path(req.getRequestURI()).build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Unhandled: ", ex);
        ErrorResponse err = ErrorResponse.builder()
            .timestamp(java.time.LocalDateTime.now())
            .status(500).error("INTERNAL_ERROR").message("Something went wrong").path(req.getRequestURI()).build();
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}