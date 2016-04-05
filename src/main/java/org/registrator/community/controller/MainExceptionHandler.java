package org.registrator.community.controller;

import org.registrator.community.exceptions.AbstractRegistratorException;
import org.registrator.community.exceptions.ResourceEntityNotFound;
import org.registrator.community.utils.HttpUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handler for application
 */
@ControllerAdvice
public class MainExceptionHandler {
    @Autowired
    private Logger logger;

    @ExceptionHandler(ResourceEntityNotFound.class)
    public ModelAndView handleResourceEntityNotFound(HttpServletRequest request, ResourceEntityNotFound exception) {
        logger.warn("Request: " + HttpUtils.getFullRequestURL(request)
                + " raised " + exception.getClass().getName());

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("resourceEntityNotFound");
        return mav;
    }


    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(HttpServletRequest request, Exception exception) {
        logger.error("Request: " + HttpUtils.getFullRequestURL(request) + " access denied!", exception);
        return "accessDenied";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleUncaughtExceptions(HttpServletRequest request, Exception exception) {

        logger.error("Request: " + HttpUtils.getFullRequestURL(request) + " uncaught exception", exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("redirect:/error");
        return mav;
    }

}

