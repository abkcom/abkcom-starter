package com.abkcom.web.flowDemo;

import com.abkcom.web.user.UserForm;

public class MakePaymentBean
{
  private String amount;
  private String cardNumber;
  private UserForm userForm = new UserForm();

  public UserForm getUserForm()
  {
    return this.userForm;
  }

  public void setUserForm(UserForm userForm)
  {
    this.userForm = userForm;
  }

  public String getAmount()
  {
    return this.amount;
  }

  public void setAmount(String amount)
  {
    this.amount = amount;
  }

  public String getCardNumber()
  {
    return this.cardNumber;
  }

  public void setCardNumber(String cardNumber)
  {
    this.cardNumber = cardNumber;
  }

}
