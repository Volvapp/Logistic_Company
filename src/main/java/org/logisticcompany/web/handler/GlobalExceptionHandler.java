package org.logisticcompany.web.handler;

import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ObjectNotFoundException.class})
    public ModelAndView handleException(ObjectNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("object-not-found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }
}
