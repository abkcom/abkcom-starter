package com.abkcom.common.util;

import java.math.BigDecimal;
import java.util.Collection;

public abstract class Assert
{
  /**
   *
   * @param string
   * @param message
   */
  public static void notEmpty(String string, String message)
  {
    if (string == null || string.trim().isEmpty())
    {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   *
   * @param string
   */
  public static void notEmpty(String string)
  {
    notEmpty(string, "[Assertion failed] - this string is required; it must not be null or empty");
  }

  /**
   *
   * @param object
   * @param message
   */
  public static void notNull(Object object, String message)
  {
    if (object == null)
    {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   *
   * @param object
   */
  public static void notNull(Object object)
  {
    notNull(object, "[Assertion failed] - this argument is required; it must not be null");
  }

  public static void number(String number, String message)
  {
      try
      {
        new BigDecimal(number);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException(message);
      }
  }

  public static void notEmpty(Collection<?> coll, String message)
  {
    if (coll == null || coll.isEmpty())
    {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notEmpty(Collection<?> coll)
  {
    notEmpty(
        coll, "[Assertion failed] - this collection is required; it must not be null or empty");
  }

  public static void notEmpty(Object[] array, String message)
  {
    if (array == null || array.length == 0)
    {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notEmpty(Object[] array)
  {
    notEmpty(array, "[Assertion failed] - this array is required; it must not be null or empty");
  }

  public static void nul(Object object)
  {
    nul(object, "[Assertion failed] - this argument must be null");
  }

  public static void nul(Object object, String message)
  {
    if (object != null)
    {
      throw new IllegalArgumentException(message);
    }
  }
}
