package com.abkcom.service;

public class SecurityException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public SecurityException()
  {
    super();
  }

  public SecurityException(String msg)
  {
    super(msg);
  }
}