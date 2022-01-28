package com.abkcom.common.util;

public class NumUtil
{
  public static boolean isDigits(String number)
  {
    if (StrUtil.isNotWhitespace(number))
    {
      for (char ch : number.toCharArray())
      {
        if (!Character.isDigit(ch))
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }

}
