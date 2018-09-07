package com.abkcom.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SelectFieldOptionalValidator extends FormValidatorHelper implements Validator
{
  @Override
  public boolean supports(Class<?> clazz)
  {
    return SelectFormField.class.equals(clazz);
  }

  @Override
  public void validate(Object o, Errors errors)
  {
    SelectFormField<?> form = (SelectFormField<?>) o;
    if (form.isSelected())
    {
      if (!form.isValidSelection())
      {
        errors.rejectValue(SELECTED_FIELD, FIELD_INVALID);
      }
    }
  }
}
