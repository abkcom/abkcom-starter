package com.abkcom.web.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;

import com.abkcom.AbkcomStarterApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AbkcomStarterApplication.class})
public class UserFormValidatorTest
{
  private UserForm userForm;
  private BindException errors;
  @Autowired
  private UserFormValidator validator;

  @Before
  public void setUp() throws Exception
  {
    userForm = new UserForm();
    errors = new BindException(userForm, "userForm");
  }

  @Test
  public void supports()
  {
    assertTrue(validator.supports(UserForm.class));
  }

  @Test
  public void validateFullName()
  {
    userForm.setFullName("Full Name");
    ValidationUtils.invokeValidator(validator, userForm, errors);
    assertNull(errors.getFieldError("fullName"));
  }

  @Test
  public void validateFullName_required()
  {
    userForm.setFullName("");
    ValidationUtils.invokeValidator(validator, userForm, errors);
    FieldError error = errors.getFieldError("fullName");
    assertEquals("err.field.required", error.getCode());
  }
}
