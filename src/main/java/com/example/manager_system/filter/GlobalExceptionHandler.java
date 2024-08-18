package com.example.manager_system.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleBadRequest(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Bad request: " + ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/exception/system_error?code=400"));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", "Page not found: " + ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/exception/system_error?code=404"));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}