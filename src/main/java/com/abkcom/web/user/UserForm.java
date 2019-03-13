package com.abkcom.web.user;

import com.abkcom.common.util.Money;
import com.abkcom.model.user.UserCountry;
import com.abkcom.service.user.UserCreate;
import com.abkcom.service.user.UserUpdate;
import com.abkcom.service.user.UserView;
import com.abkcom.web.SelectFormField;

public class UserForm implements UserCreate, UserUpdate {
  private Long id;
  private String fullName;
  private String username;
  private String password;
  private String email;
  private SelectFormField<UserCountry> homeCountryForm;
  private Long age;
  private Money annualIncome;

  public UserForm()
  {
    homeCountryForm = new SelectFormField<>(UserCountry.list(), "name", "name");
  }
  
  public Long getId()
  {
    return id;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  
  @Override
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getEmail() {
    return email;
  }
  
  public SelectFormField<UserCountry> getHomeCountryForm()
  {
    return homeCountryForm;
  }
  
  @Override
  public UserCountry getHomeCountry()
  {
    return homeCountryForm.getSelectedChoice();
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  public Long getAge()
  {
    return age;
  }
  
  public void setAge(Long age)
  {
    this.age = age;
  }

  public Money getAnnualIncome()
  {
    return annualIncome;
  }

  public void setAnnualIncomee(Money annualIncome)
  {
    this.annualIncome = annualIncome;
  }
  
  public void populateForm(UserView user)
  {
    this.fullName = user.getFullName();
    this.email = user.getEmail();
    this.homeCountryForm.select(user.getHomeCountry());
  }
}
