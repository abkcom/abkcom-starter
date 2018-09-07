package com.abkcom.repo.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.abkcom.model.user.User;
import com.abkcom.service.user.UserRepo;

@Repository
public class UserRepoInMemory implements UserRepo
{
  private static AtomicLong idgen = new AtomicLong();
  private Map<Long, User> users;

  public UserRepoInMemory()
  {
    users = new LinkedHashMap<Long, User>();
    init();
  }

  private void init()
  {
    User u = new User();
    u.setId(idgen.getAndIncrement());
    u.setFullName("Pasha");
    u.setUsername("pasha_user");
    u.setEmail("pasha@test.com");
    users.put(u.getId(), u);
    u = new User();
    u.setId(idgen.getAndIncrement());
    u.setFullName("John");
    u.setUsername("john_user");
    u.setEmail("john@test.com");
    users.put(u.getId(), u);
  }
  
  @Override
  public List<User> getUsers()
  {
    return new ArrayList<User>(users.values());
  }

  @Override
  public User getUser(long id)
  {
    return users.get(id);
  }

  @Override
  public void addUser(User user)
  {
    long id = idgen.getAndIncrement(); 
    user.setId(id);
    users.put(id, user);
  }
  
  @Override
  public void updateUser(User user)
  {
    users.put(user.getId(), user);
  }

  @Override
  public void deleteUser(long id)
  {
    users.remove(id);
  }
}
