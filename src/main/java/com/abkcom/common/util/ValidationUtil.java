package com.abkcom.common.util;


public class ValidationUtil
{
  
  public static boolean isValidMoney(String value)
  {
    String EXPRESSION = "^\\-?([1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*(\\.[0-9]{1,2})?|[1-9]{1}[0-9]{0,}(\\.[0-9]{1,2})?|0(\\.[0-9]{1,2})?|(\\.[0-9]{1,2}))$";
    return StrUtil.isNotWhitespace(value) && value.matches(EXPRESSION);
  }

}
