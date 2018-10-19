package com.abkcom.service.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abkcom.model.user.User;

@Service
public class UserCrudServiceImpl implements UserCrudService
{
  @Autowired
  private UserRepo userRepo;

  @Override
  public UserView getUser(long id)
  {
    User user = userRepo.getUser(id);
    if (user == null)
    {
      throw new UserNotFoundException();
    }
    return user;
  }

  @Override
  public Collection<UserView> getAllUsers()
  {
    return Collections.unmodifiableCollection(userRepo.getUsers());
  }

  @Override
  public UserView addUser(UserCreate userDto)
  {
    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setFullName(userDto.getFullName());
    user.setUsername(userDto.getUsername());
    user.setPassword(userDto.getPassword());
    user.setHomeCountry(userDto.getHomeCountry());
    userRepo.addUser(user);
    return user;
  }

  @Override
  public void editUser(long userId, UserUpdate userDto)
  {
    User user = userRepo.getUser(userId);
    user.setEmail(userDto.getEmail());
    user.setFullName(userDto.getFullName());
    user.setHomeCountry(userDto.getHomeCountry());
    userRepo.updateUser(user);
  }

  @Override
  public void deleteUser(long id)
  {
    userRepo.deleteUser(id);
  }

}
