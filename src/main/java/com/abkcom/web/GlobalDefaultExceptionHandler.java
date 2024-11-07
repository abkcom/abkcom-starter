package com.abkcom.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.abkcom.service.EntityNotFoundException;
import com.abkcom.service.EntityPersistenceException;
import com.abkcom.service.SecurityException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler
{
  public static final String DEFAULT_ERROR_VIEW = "/error";
  public static final String ERROR_VIEW_404 = "/error404";
  public static final String ERROR_VIEW_403 = "/error403";

  // @ExceptionHandler()
  // public ModelAndView errorHandler404(HttpServletRequest req,
  // EntityNotFoundException e)
  // {
  // ModelAndView mav = new ModelAndView();
  // mav.addObject("exception", e);
  // mav.addObject("url", req.getRequestURL());
  // mav.setViewName(ERROR_VIEW_404);
  // return mav;
  // }

  @ExceptionHandler()
  public ModelAndView errorHandler404(HttpServletRequest req, EntityNotFoundException e)
  {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    req.setAttribute("errorMessage", new PageMessage("err.notFound"));
    mav.setViewName("/welcome");
    return mav;
  }

  @ExceptionHandler()
  public ModelAndView errorHandlerPersistence(HttpServletRequest req, EntityPersistenceException e)
  {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    req.setAttribute("errorMessage", new PageMessage("err.generic"));
    mav.setViewName("/welcome");
    return mav;
  }

  @ExceptionHandler()
  public ModelAndView errorHandlerSecurity(HttpServletRequest req, SecurityException e)
  {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(ERROR_VIEW_403);
    return mav;
  }

  @ExceptionHandler()
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e)
  {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    return mav;
  }
}