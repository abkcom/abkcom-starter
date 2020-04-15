package com.abkcom.service.user;

import java.util.Collection;
import java.util.Optional;

public interface UserCrudService
{
  UserView getUser(long userId) throws UserNotFoundException;

  Optional<UserView> findUser(long userId);

  Collection<UserView> getAllUsers();

  UserView addUser(UserCreate data);

  void addUserError();

  void editUser(long userId, UserUpdate data);

  void deleteUser(long userId);

  void secretPage();

  boolean isSecretPageAllowed();
}
