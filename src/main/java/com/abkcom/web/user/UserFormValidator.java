package com.abkcom.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abkcom.web.FormValidatorHelper;
import com.abkcom.web.SelectFieldRequiredValidator;

@Component
public class UserFormValidator extends FormValidatorHelper implements Validator
{
  @Autowired
  private SelectFieldRequiredValidator selectRequiredValidator;
  @Override
  public boolean supports(Class<?> clazz)
  {
    return UserForm.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors)
  {
    UserForm u = (UserForm) target;
    validateFullName(errors, u);
    validateEmail(errors, u);
    validateUsername(errors, u);
    validatePassword(errors, u);
    validateHomeCountry(errors, u);
  }
  
  private void validateFullName(Errors errors, UserForm u)
  {
    rejectIfEmptyOrWhitespace(errors, "fullName");
  }
  
  private void validateEmail(Errors errors, UserForm u)
  {
    rejectIfEmptyOrWhitespace(errors, "email");
  }
  
  private void validateUsername(Errors errors, UserForm u)
  {
    if(u.getId() == null)
    rejectIfEmptyOrWhitespace(errors, "username");
  }
  
  private void validatePassword(Errors errors, UserForm u)
  {
    if(u.getId() == null)
    rejectIfEmptyOrWhitespace(errors, "password");
  }
  
  private void validateHomeCountry(Errors errors, UserForm u)
  {
    try
    {
      errors.pushNestedPath("homeCountryForm");
      ValidationUtils.invokeValidator(selectRequiredValidator, u.getHomeCountryForm(), errors);
    }
    finally
    {
      errors.popNestedPath();
    }
  }
}
