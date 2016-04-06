package org.registrator.community.controller;

import org.registrator.community.exceptions.BadInputDataException;
import org.registrator.community.validator.MassUserOpsValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handler for application
 */
@ControllerAdvice
public class MainExceptionHandler {
    @Autowired
    private Logger logger;

    public static final String DEFAULT_ERROR_VIEW = "error";

//    @ExceptionHandler(Exception.class)
    public ModelAndView resourceNotFoundHandler(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
    
    @ExceptionHandler(BadInputDataException.class)
    @ResponseBody
    public String handleCustomException(BadInputDataException ex) {
          return MassUserOpsValidator.WRONG_INPUT;

    }

}
