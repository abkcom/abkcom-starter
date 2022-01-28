package com.abkcom.web.flowDemo;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.abkcom.web.FormValidatorHelper;

public class CardNumberFormValidator extends FormValidatorHelper implements Validator
{
  @Override
  public boolean supports(Class<?> clazz)
  {
    return CardNumberForm.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors)
  {
    rejectIfNotDijits(errors, "cardNumber");
  }

}
