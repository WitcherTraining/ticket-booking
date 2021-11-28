package com.epam.ticket.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonExceptionHandlerController extends AbstractHandlerExceptionResolver {
    private static final Logger LOGGER = Logger.getLogger(CommonExceptionHandlerController.class);

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        LOGGER.error("Handling of [" + ex.getClass().getName() + "] failed with next Exception: ", ex);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorPage");
        return modelAndView;
    }

}
