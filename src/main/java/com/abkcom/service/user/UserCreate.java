package com.abkcom.service.user;

import com.abkcom.model.user.UserCountry;

public interface UserCreate
{
  String getFullName();

  String getUsername();
  
  String getPassword();

  String getEmail();
  
  UserCountry getHomeCountry();
}
