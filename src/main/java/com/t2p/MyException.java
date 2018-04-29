package com.t2p;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyException extends Exception {
    @ExceptionHandler(value = {Exception.class})
    protected ModelAndView handleConflict(Exception ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("msg", ex.getMessage());
        return modelAndView;

    }
}
