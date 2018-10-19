package com.abkcom.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class AbstractController
{
  private static final String SUCCESS_KEY = "successMessage";
  private static final String ERROR_KEY = "errorMessage";

  @InitBinder
  public void defaultInitBinder(WebDataBinder binder)
  {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  protected void addPageError(RedirectAttributes ra, String resource)
  {
    addPageError(ra, resource, "");
  }

  protected void addPageError(RedirectAttributes ra, String resource, String... attrs)
  {
    addFlashAttribute(ra, ERROR_KEY, resource, attrs);
  }

  protected void addPageError(HttpServletRequest req, String resource)
  {
    addPageError(req, resource, "");
  }

  protected void addPageError(HttpServletRequest req, String resource, String... attrs)
  {
    setAttribute(req, ERROR_KEY, resource, attrs);
  }

  protected void addPageMessage(HttpServletRequest req, String string)
  {
    addPageMessage(req, string, "");
  }

  protected void addPageMessage(HttpServletRequest req, String resource, String... attrs)
  {
    setAttribute(req, SUCCESS_KEY, resource, attrs);
  }

  protected void addPageMessage(RedirectAttributes ra, String resource)
  {
    addPageMessage(ra, resource, "");
  }

  protected void addPageMessage(RedirectAttributes ra, String resource, String... attrs)
  {
    addFlashAttribute(ra, SUCCESS_KEY, resource, attrs);
  }

  private void addFlashAttribute(RedirectAttributes ra, String key, String code, String... attrs)
  {
    ra.addFlashAttribute(key, new PageMessage(code, attrs));
  }

  private void setAttribute(HttpServletRequest req, String key, String code, String... attrs)
  {
    req.setAttribute(key, new PageMessage(code, attrs));
  }
}
