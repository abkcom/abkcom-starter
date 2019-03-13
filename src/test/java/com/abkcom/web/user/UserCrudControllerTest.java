package com.abkcom.web.user;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.abkcom.common.util.CollUtil;
import com.abkcom.model.user.User;
import com.abkcom.model.user.UserCountry;
import com.abkcom.service.user.UserCrudService;
import com.abkcom.service.user.UserNotFoundException;
import com.abkcom.web.PageMessage;

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
  }

  @Test
  public void viewUser() throws Exception
  {
    when(userServiceMock.getUser(0L)).thenReturn(user1);
    mvcMoc.perform(get("/users/0")).andExpect(status().isOk())
    .andExpect(view().name("/user/viewUser"))
    .andExpect(model().attribute("user", is(user1)));
    verify(userServiceMock, times(1)).getUser(0l);
    verifyNoMoreInteractions(userServiceMock);
  }

  @Test
  public void getNonExistingUser() throws Exception
  {
    when(userServiceMock.getUser(0L)).thenThrow(new UserNotFoundException());
    mvcMoc.perform(get("/users/0"))
    .andExpect(status().isFound())
    .andExpect(view().name("redirect:/users"))
    .andExpect(flash().attribute(UserCrudController.ERROR_KEY, isA(PageMessage.class)));
    //how to check error key?
    verify(userServiceMock, times(1)).getUser(0L);
    verifyNoMoreInteractions(userServiceMock);
  }

  @Test
  public void listUsers() throws Exception
  {
    when(userServiceMock.getAllUsers()).thenReturn(CollUtil.toList(user1, user2));
    mvcMoc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(view().name("/user/listUsers"))
        //.andExpect(forwardedUrl("/WEB-INF/page/user/listUsers.jspx"))
        .andExpect(model().attribute("users", hasSize(2)))
        .andExpect(model().attribute("users", hasItem(
            allOf(
                hasProperty("email", is("test@email.com")),
                hasProperty("fullName", is("Peter Doe")),
                hasProperty("username", is("peterdoe"))
            )
    )))
    .andExpect(model().attribute("users", hasItem(
            allOf(
                hasProperty("email", is("test2@email.com")),
                hasProperty("fullName", is("John Doe")),
                hasProperty("username", is("johndoe"))
            )
    )));
    verify(userServiceMock, times(1)).getAllUsers();
    verifyNoMoreInteractions(userServiceMock);
  }

  @Test
  public void addUserView() throws Exception
  {
    mvcMoc.perform(get("/users/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("/user/addUser"));
    verifyZeroInteractions(userServiceMock);
  }
  
  @Test
  public void addUser() throws Exception
  {
    mvcMoc.perform(post("/users/add")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("fullName", "John Doe")
        .param("username", "johndoe")
        .param("email", "test@test.com")
        .param("password", "123456")
        .param("homeCountryForm.selectedKey", "USA")
        .requestAttr("userForm", new UserForm()))
    .andExpect(status().isFound())
    .andExpect(view().name("redirect:/users"));
    
    ArgumentCaptor<UserForm> formObjectArgument = ArgumentCaptor.forClass(UserForm.class);
    verify(userServiceMock, times(1)).addUser(formObjectArgument.capture());
    verifyNoMoreInteractions(userServiceMock);
    UserForm formObject = formObjectArgument.getValue();
    assertEquals("John Doe", formObject.getFullName());
    assertEquals("johndoe", formObject.getUsername());
    assertEquals("test@test.com", formObject.getEmail());
    assertEquals("123456", formObject.getPassword());
    assertEquals(UserCountry.USA, formObject.getHomeCountry());
  }

  @Test
  public void deleteUser() throws Exception
  {
    mvcMoc.perform(post("/users/{id}/delete", 0L))
    .andExpect(status().isFound())
    .andExpect(view().name("redirect:/users"));
    verify(userServiceMock, times(1)).deleteUser(0L);
    verifyNoMoreInteractions(userServiceMock);
  }

  @Test
  public void updateUser() throws Exception
  {
    Long id = 0L;
    mvcMoc.perform(post("/users/{id}/edit", id)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("fullName", "John Doe")
        .param("email", "test@test.com")
        .param("homeCountryForm.selectedKey", "USA")
        .requestAttr("userForm", new UserForm()))
    .andExpect(status().isFound())
    .andExpect(view().name("redirect:/users/" + id));
    
    ArgumentCaptor<UserForm> formObjectArgument = ArgumentCaptor.forClass(UserForm.class);
    ArgumentCaptor<Long> idObjectArgument = ArgumentCaptor.forClass(Long.class);
    verify(userServiceMock, times(1)).editUser(idObjectArgument.capture(), formObjectArgument.capture());
    verifyNoMoreInteractions(userServiceMock);
    UserForm formObject = formObjectArgument.getValue();
    //assertEquals("John Doe", formObject.getFullName());
    assertThat("John Doe", equalTo(formObject.getFullName()));
    assertEquals("test@test.com", formObject.getEmail());
    assertEquals(UserCountry.USA, formObject.getHomeCountry());
    assertEquals(id, idObjectArgument.getValue());
  }

}
