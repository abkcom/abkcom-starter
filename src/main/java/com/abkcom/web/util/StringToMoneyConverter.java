package com.abkcom.web.util;

import org.springframework.core.convert.converter.Converter;

import com.abkcom.common.util.Money;
import com.abkcom.common.util.StrUtil;
import com.abkcom.common.util.ValidationUtil;

public class StringToMoneyConverter  implements Converter<String, Money> 
{
  @Override
  public Money convert(String money)
  {
    if(StrUtil.isNotWhitespace(money))
    {
      asserValidMoney(money);
      return Money.toMoney(money);
    }
    return null;
  }

  private void asserValidMoney(String money)
  {
    if(!ValidationUtil.isValidMoney(money))
    {
      throw new NumberFormatException();
    }
  }
}

