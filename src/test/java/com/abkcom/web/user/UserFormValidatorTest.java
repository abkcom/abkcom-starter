package com.abkcom.web.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;

public class UserFormValidatorTest
{
  private UserForm userForm;
  private BindException errors;
  @InjectMocks
  private UserFormValidator validator;

  @BeforeEach
  public void setUp() throws Exception
  {
    this.validator = new UserFormValidator();
    this.userForm = new UserForm();
    this.errors = new BindException(this.userForm, "userForm");
  }

  @Test
  public void supports()
  {
    assertTrue(this.validator.supports(UserForm.class));
  }

  @Test
  public void validateFullName()
  {
    this.userForm.setFullName("Full Name");
    ValidationUtils.invokeValidator(this.validator, this.userForm, this.errors);
    assertNull(this.errors.getFieldError("fullName"));
  }

  @Test
  public void validateFullName_required()
  {
    this.userForm.setFullName("");
    ValidationUtils.invokeValidator(this.validator, this.userForm, this.errors);
    FieldError error = this.errors.getFieldError("fullName");
    assertEquals("err.field.required", error.getCode());
  }
}
