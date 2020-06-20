package com.gz.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @authod wu
 * @date 2020/5/11 21:41
 */
@ControllerAdvice
public class ExceptionControllerHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerHandler.class);

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL: {}, Exception: {}",request.getRequestURL(),e.getMessage());

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");

        return mv;
    }
}
