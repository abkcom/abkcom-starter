package com.abkcom.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abkcom.common.util.BeanUtil;

public class SelectFormField<T> implements Serializable
{
  private static final long serialVersionUID = -3197120330751003548L;
  private String selectedKey;
  private Map<String, String> options;
  private List<T> choices;
  private String keyProp;

  public SelectFormField(List<T> choices, String keyProp, String valueProp)
  {
    this(choices, keyProp, valueProp, true);
  }

  public SelectFormField(List<T> choices, String keyProp, String valueProp, boolean isEmptyOption)
  {
    selectedKey = "";
    options = new LinkedHashMap<String, String>();
    this.choices = choices;
    this.keyProp = keyProp;
    setOptions(choices, keyProp, valueProp, isEmptyOption);
  }

  private void setOptions(List<T> choices, String keyProp, String valueProp, boolean isEmptyOption)
  {
    options.put("", "");
    for (Map.Entry<?, ?> me : BeanUtil.toMap(choices, keyProp, valueProp).entrySet())
    {
      options.put(String.valueOf(me.getKey()), String.valueOf(me.getValue()));
    }
  }

  public String getSelectedKey()
  {
    return selectedKey;
  }

  public String getSelectedValue()
  {
    return options.get(selectedKey);
  }

  public void setSelectedKey(String selected)
  {
    this.selectedKey = selected;
  }

  public Map<String, String> getOptions()
  {
    return options;
  }

  public T getSelectedChoice()
  {
    for (T c : choices)
    {
      String keyValue = keyValue(c);
      if (keyValue.equals(selectedKey))
      {
        return c;
      }
    }
    return null;
  }

  public boolean isSelected()
  {
    return selectedKey != null && !selectedKey.trim().isEmpty();
  }

  public boolean isValidSelection()
  {
    return getSelectedChoice() != null;
  }

  public void select(Long id)
  {
    select(id == null ? null : String.valueOf(id));
  }

  public void select(String keyValue)
  {
    selectedKey = "";
    if (keyValue != null)
    {
      for (Map.Entry<String, String> option : options.entrySet())
      {
        if (keyValue.equals(option.getKey()))
        {
          selectedKey = keyValue;
        }
      }
    }
  }

  public void select(T option)
  {
    select(option == null ? null : keyValue(option));
  }

  private String keyValue(T option)
  {
    return String.valueOf(BeanUtil.invokeGetter(option, keyProp));
  }

  protected void setOptions(Map<String, String> options)
  {
    this.options = options;
  }
}
