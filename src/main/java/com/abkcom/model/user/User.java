package com.abkcom.model.user;

import com.abkcom.service.user.UserView;

public class User implements UserView
{
  private Long id;
  private String fullName;
  private String username;
  private String password;
  private String email;
  private UserCountry homeCountry;

  @Override
  public Long getId()
  {
    return id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }

  @Override
  public String getFullName()
  {
    return fullName;
  }

  public void setFullName(String name)
  {
    fullName = name;
  }

  @Override
  public String getUsername()
  {
    return username;
  }

  public void setUsername(String uname)
  {
    username = uname;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String pwd)
  {
    password = pwd;
  }

  @Override
  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }
  

  @Override
  public UserCountry getHomeCountry()
  {
    return homeCountry;
  }

  public void setHomeCountry(UserCountry homeCountry)
  {
    this.homeCountry = homeCountry;
  }

  @Override
  public String toString()
  {
    return "(" + fullName + "," + username + ")";
  }

  
}