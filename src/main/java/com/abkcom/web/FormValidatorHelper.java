package com.abkcom.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.abkcom.common.util.Money;
import com.abkcom.common.util.NumUtil;

public class FormValidatorHelper
{
  protected static final String SELECTED_FIELD = "selectedKey";
  protected static final String FIELD_REQUIRED = "err.field.required";
  protected static final String FIELD_INVALID = "err.field.invalid";
  protected static final String FIELD_NOT_UNIQUE = "err.field.not.unique";

  public static boolean rejectIfEmptyOrWhitespace(Errors errors, String field)
  {
    return rejectIfEmptyOrWhitespace(errors, field, FIELD_REQUIRED);
  }

  public static boolean rejectIfEmptyOrWhitespace(Errors errors, String field, String messageKey)
  {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, messageKey);
    return errors.hasFieldErrors(field);
  }

  public static boolean rejectIfNotMoney(Errors errors, String field)
  {
    if (!rejectIfEmptyOrWhitespace(errors, field))
    {
      Object value = errors.getFieldValue(field);
      if (!Money.isValid(String.valueOf(value)))
      {
        errors.rejectValue(field, FIELD_INVALID);
      }
      else
      {
        Money amount = Money.toMoney(String.valueOf(value));
        if (amount.isNegativeOrZero())
        {
          errors.rejectValue(field, FIELD_INVALID);
        }
      }
    }
    return errors.hasFieldErrors(field);
  }

  public static boolean rejectIfNotDijits(Errors errors, String field)
  {
    if (!rejectIfEmptyOrWhitespace(errors, field))
    {
      Object value = errors.getFieldValue(field);
      if (!NumUtil.isDigits(String.valueOf(value)))
      {
        errors.rejectValue(field, FIELD_INVALID);
      }
    }
    return errors.hasFieldErrors(field);
  }

  public static boolean rejectInvalid(Errors errors, String field)
  {
    return rejectInvalid(errors, field, FIELD_INVALID);
  }

  public static boolean rejectInvalid(Errors errors, String field, String messageKey)
  {
    errors.rejectValue(field, messageKey);
    return errors.hasFieldErrors(field);
  }

  public static boolean isEmpty(String value)
  {
    return value == null || value.trim().isEmpty();
  }

  public static boolean isNotEmpty(String value)
  {
    return !isEmpty(value);
  }
}
