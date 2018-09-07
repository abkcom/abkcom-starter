package com.abkcom.service.user;

import java.util.List;

import com.abkcom.model.user.User;

public interface UserRepo
{
  List<User> getUsers();
  
  User getUser(long id);
  
  void addUser(User user);
  
  void updateUser(User user);
  
  void deleteUser(long id);
}
