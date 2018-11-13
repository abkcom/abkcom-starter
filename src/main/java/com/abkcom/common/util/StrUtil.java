package com.abkcom.common.util;

public class StrUtil
{
  public static String trimNullToEmpty(String value)
  {
    return value == null ? "" : value.trim();
  }

  public static boolean isWhitespace(String value)
  {
    return trimNullToEmpty(value).isEmpty();
  }

  public static boolean isNotWhitespace(String value)
  {
    return !isWhitespace(value);
  }

  public static boolean isAlphaNumeric(String value)
  {
    if (isNotWhitespace(value))
    {
      for (char c : value.toCharArray())
      {
        if (!Character.isLetterOrDigit(c))
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  /**
   * -$1,000.00 -> -1000.00
   */
  public static String stripMoney(String money)
  {
    return money.replaceAll("[^\\d.-]", "");
  }

}
