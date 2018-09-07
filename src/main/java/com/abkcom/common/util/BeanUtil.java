package com.abkcom.common.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class BeanUtil
{

  public static void copyProperties(Object orig, Object dest, String... ignore)
  {
    try
    {
      BeanUtils.copyProperties(orig, dest, ignore);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Unable to copy properties!", e);
    }
  }

  public static void copyProperties(Object orig, Object dest)
  {
    try
    {
      BeanUtils.copyProperties(orig, dest);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Unable to copy properties!", e);
    }
  }
  
  public static Map<?, ?> toMap(Collection<?> coll, String keyProp, String valueProp)
  {
    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
    for (Object o : coll)
    {
      Object key = invokeGetter(o, keyProp);
      Object value = invokeGetter(o, valueProp);
      if (key != null)
      {
        map.put(key, value);
      }
    }
    return map;
  }

  public static Object invokeGetter(Object o, String prop)
  {
    try
    {
      Method method = o.getClass().getMethod(propToGetMethod(prop));
      Object value = method.invoke(o);
      return value;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  private static String propToGetMethod(String prop)
  {
    return "get" + StringUtils.capitalize(prop);
  }
}
