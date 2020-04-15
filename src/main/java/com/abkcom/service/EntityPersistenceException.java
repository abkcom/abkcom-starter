package com.abkcom.service;

public class EntityPersistenceException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public EntityPersistenceException()
  {
    super();
  }

  public EntityPersistenceException(String msg)
  {
    super(msg);
  }
}