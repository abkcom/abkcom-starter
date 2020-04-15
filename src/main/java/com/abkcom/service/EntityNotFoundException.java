package com.abkcom.service;

public class EntityNotFoundException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException()
  {
    super();
  }

  public EntityNotFoundException(String msg)
  {
    super(msg);
  }
}