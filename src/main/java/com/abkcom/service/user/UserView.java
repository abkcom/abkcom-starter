package com.abkcom.service.user;

import com.abkcom.model.user.UserCountry;

public interface UserView
{
  Long getId();

  String getFullName();

  String getUsername();

  String getEmail();
  
  UserCountry getHomeCountry();

}
