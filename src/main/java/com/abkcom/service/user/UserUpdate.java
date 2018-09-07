package com.abkcom.service.user;

import com.abkcom.model.user.UserCountry;

public interface UserUpdate
{
  String getFullName();

  String getEmail();
  
  UserCountry getHomeCountry();
}
