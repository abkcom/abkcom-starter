package com.abkcom.model.user;

import java.util.List;

import com.abkcom.common.util.CollUtil;

public enum UserCountry
{
  USA, CHINA;
  
  public String getName()
  {
    return name();
  }

  public static List<UserCountry> list()
  {
    return CollUtil.toList(values());
  }
}
