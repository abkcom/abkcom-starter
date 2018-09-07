package com.abkcom.service.user;

import java.util.Collection;

public interface UserCrudService
{
  UserView getUser(long userId);

  Collection<UserView> getAllUsers();

  UserView addUser(UserCreate data);

  void updateUser(long userId, UserUpdate data);

  void deleteUser(long userId);
}
