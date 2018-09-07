package com.abkcom.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SelectFieldRequiredValidator extends FormValidatorHelper implements Validator
{
  private String requiredMessageKey;

  public SelectFieldRequiredValidator()
  {
    this.requiredMessageKey = FIELD_REQUIRED;
  }

  public SelectFieldRequiredValidator(String requiredMessageKey)
  {
    this.requiredMessageKey = requiredMessageKey;
  }

  @Override
  public boolean supports(Class<?> clazz)
  {
    return SelectFormField.class.equals(clazz);
  }

  @Override
  public void validate(Object o, Errors errors)
  {
    SelectFormField<?> form = (SelectFormField<?>) o;
    if (!rejectIfEmptyOrWhitespace(errors, SELECTED_FIELD, requiredMessageKey))
    {
      if (!form.isValidSelection())
      {
        errors.rejectValue(SELECTED_FIELD, FIELD_INVALID);
      }
    }
  }
}
