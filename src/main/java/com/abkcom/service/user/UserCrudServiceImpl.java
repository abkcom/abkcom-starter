package com.abkcom.service.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abkcom.model.user.User;
import com.abkcom.service.EntityNotFoundException;
import com.abkcom.service.EntityPersistenceException;
import com.abkcom.service.SecurityException;

@Service
public class UserCrudServiceImpl implements UserCrudService
{
  @Autowired
  private UserRepo userRepo;

  @Override
  public UserView getUser(long id)
  {
    User user = this.userRepo.getUser(id);
    if (user == null)
    {
      // throw new UserNotFoundException();
      throw new EntityNotFoundException("User not found: " + id);
      // throw new NullPointerException("User not found: " + id);
    }
    return user;
  }

  @Override
  public Optional<UserView> findUser(long id)
  {
    return Optional.ofNullable(this.userRepo.getUser(id));
  }

  @Override
  public Collection<UserView> getAllUsers()
  {
    return Collections.unmodifiableCollection(this.userRepo.getUsers());
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
    this.userRepo.addUser(user);
    return user;
  }

  @Override
  public void addUserError()
  {
    throw new EntityPersistenceException("Cant save user!");
  }

  @Override
  public void editUser(long userId, UserUpdate userDto)
  {
    User user = this.userRepo.getUser(userId);
    user.setEmail(userDto.getEmail());
    user.setFullName(userDto.getFullName());
    user.setHomeCountry(userDto.getHomeCountry());
    this.userRepo.updateUser(user);
  }

  @Override
  public void deleteUser(long id)
  {
    this.userRepo.deleteUser(id);
  }

  @Override
  public void secretPage()
  {
    assertSecretPageAllowed();
    // do something secret
    System.out.println("My password is 123456");

  }

  private void assertSecretPageAllowed()
  {
    if (!isSecretPageAllowed())
    {
      throw new SecurityException("You are bad!");
    }

  }

  @Override
  public boolean isSecretPageAllowed()
  {
    return false;
  }
}
