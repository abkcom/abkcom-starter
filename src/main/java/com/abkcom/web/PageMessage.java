package com.abkcom.web;

import java.util.List;

import com.abkcom.common.util.CollUtil;

public class PageMessage
{
  private String key;
  private String[] attrs;

  public PageMessage(String key, String[] attrs)
  {
    this.key = key;
    this.attrs = attrs;
  }

  public String getKey()
  {
    return key;
  }

  public String[] getAttrs()
  {
    return attrs;
  }

  public List<String> getAttrsAsList()
  {
    return CollUtil.toList(attrs);
  }
}
