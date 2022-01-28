package com.abkcom.web.flowDemo;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.abkcom.common.util.Money;
import com.abkcom.web.FormValidatorHelper;

public class AmountFormValidator extends FormValidatorHelper implements Validator
{
  @Override
  public boolean supports(Class<?> clazz)
  {
    return AmountForm.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors)
  {
    if (!rejectIfNotMoney(errors, "amount"))
    {
      AmountForm form = (AmountForm) target;
      Money amt = Money.toMoney(form.getAmount());
      if (amt.lt(Money.toMoney("10")))
      {
        rejectInvalid(errors, "amount", "err.amount.invalid");
      }
    }
  }

}
