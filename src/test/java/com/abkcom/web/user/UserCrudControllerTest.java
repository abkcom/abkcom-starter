package com.abkcom.web.user;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.abkcom.common.util.CollUtil;
import com.abkcom.model.user.User;
import com.abkcom.service.user.UserCreate;
import com.abkcom.service.user.UserCrudService;
import com.abkcom.service.user.UserNotFoundException;
import com.abkcom.service.user.UserUpdate;

public class UserCrudControllerTest
{
  @InjectMocks
  private UserCrudController controller;
  @Mock
  private UserCrudService userServiceMock;
  private MockMvc mvcMoc;
  private User user1;
  private User user2;

  @Before
  public void setUp() throws Exception
  {
    MockitoAnnotations.initMocks(this);
    mvcMoc = MockMvcBuilders.standaloneSetup(controller).build();

    setupUsers();
  }

  private void setupUsers()
  {
    user1 = new User();
    user1.setEmail("test@email.com");
    user1.setFullName("Peter Doe");
    user1.setUsername("peterdoe");
    user2 = new User();
    user2.setEmail("test2@email.com");
    user2.setFullName("John Doe");
    user2.setUsername("johndoe");
    when(userServiceMock.getUser(0L)).thenReturn(user1);
    when(userServiceMock.getUser(1L)).thenReturn(user2);
    when(userServiceMock.getAllUsers()).thenReturn(CollUtil.toList(user1, user2));
  }

  @Test
  public void viewUser() throws Exception
  {
    mvcMoc.perform(get("/users/0")).andDo(print())
        // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.email", is("test@email.com")))
        .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/users/0"))));

  }

  @Test
  public void getNonExistingUser() throws Exception
  {
    when(userServiceMock.getUser(0L)).thenThrow(new UserNotFoundException());

    mvcMoc.perform(get("/users/0")).andExpect(status().isNotFound());
  }

  @Test
  public void listUsers() throws Exception
  {
    mvcMoc
        .perform(get("/users")).andDo(print())
        .andExpect(jsonPath("$.users[0].email", is("test@email.com")))
        .andExpect(jsonPath("$.users[0].links[*].href", hasItem(endsWith("/users/0"))))
        .andExpect(jsonPath("$.users[1].email", is("test2@email.com")))
        .andExpect(jsonPath("$.users[1].links[*].href", hasItem(endsWith("/users/1"))))
        .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/users"))));
  }

  @Test
  public void addUser() throws Exception
  {
    mvcMoc
        .perform(
            post("/users")
                .content(
                    "{\"fullName\":\"Peter Doe\",\"username\":\"peterdoe\",\"email\":\"test@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andExpect(content().string(""))
        .andDo(print());
    verify(userServiceMock).addUser(any(UserCreate.class));
  }

  @Test
  public void deleteUser() throws Exception
  {
    mvcMoc.perform(delete("/users/{id}", 0L)).andExpect(status().isOk()).andDo(
        print());
    verify(userServiceMock).deleteUser(0L);
  }

  @Test
  public void updateUser() throws Exception
  {
    mvcMoc
        .perform(
            put("/users")
                .content(
                    "{\"id\":\"0\",\"fullName\":\"Peter Doe\",\"username\":\"peterdoe\",\"email\":\"test@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(""))
        .andDo(print());
    verify(userServiceMock).editUser(0L, any(UserUpdate.class));
  }

}
