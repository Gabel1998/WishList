package com.example.wishlist.Controller;

/// ==========================================
/// =     håndtering af ERROR redirects      =
/// =              © A R S A                 =
/// ==========================================

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // catch 404 error (Page Not Found)
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Model model) {
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        return "error";
    }

    // alle andre errors (500 Internal Server Error, ofl)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerError(Exception e, Model model) {
        model.addAttribute("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }
}
