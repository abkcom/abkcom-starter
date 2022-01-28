package com.abkcom.web.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

  @BeforeEach
  public void setUp() throws Exception
  {
    MockitoAnnotations.openMocks(this);
    this.mvcMoc = MockMvcBuilders.standaloneSetup(this.controller).build();

    setupUsers();
  }

  private void setupUsers()
  {
    this.user1 = new User();
    this.user1.setEmail("test@email.com");
    this.user1.setFullName("Peter Doe");
    this.user1.setUsername("peterdoe");
    this.user2 = new User();
    this.user2.setEmail("test2@email.com");
    this.user2.setFullName("John Doe");
    this.user2.setUsername("johndoe");
  }

  @Test
  public void viewUser() throws Exception
  {
    when(this.userServiceMock.getUser(0L)).thenReturn(this.user1);
    this.mvcMoc.perform(get("/users/0")).andExpect(status().isOk()).andExpect(view().name("/user/viewUser"))
        .andExpect(model().attribute("user", is(this.user1)));
    verify(this.userServiceMock, times(1)).getUser(0l);
    verifyNoMoreInteractions(this.userServiceMock);
  }

  @Test
  public void getNonExistingUser() throws Exception
  {
    when(this.userServiceMock.getUser(0L)).thenThrow(new UserNotFoundException());
    this.mvcMoc.perform(get("/users/0")).andExpect(status().isFound()).andExpect(view().name("redirect:/users"))
        .andExpect(flash().attribute(UserCrudController.ERROR_KEY, isA(PageMessage.class)));
    // how to check error key?
    verify(this.userServiceMock, times(1)).getUser(0L);
    verifyNoMoreInteractions(this.userServiceMock);
  }

  @Test
  public void listUsers() throws Exception
  {
    when(this.userServiceMock.getAllUsers()).thenReturn(CollUtil.toList(this.user1, this.user2));
    this.mvcMoc.perform(get("/users")).andExpect(status().isOk()).andExpect(view().name("/user/listUsers"))
        // .andExpect(forwardedUrl("/WEB-INF/page/user/listUsers.jspx"))
        .andExpect(model().attribute("users", hasSize(2)))
        .andExpect(model().attribute("users",
            hasItem(allOf(hasProperty("email", is("test@email.com")), hasProperty("fullName", is("Peter Doe")),
                hasProperty("username", is("peterdoe"))))))
        .andExpect(model().attribute("users", hasItem(allOf(hasProperty("email", is("test2@email.com")),
            hasProperty("fullName", is("John Doe")), hasProperty("username", is("johndoe"))))));
    verify(this.userServiceMock, times(1)).getAllUsers();
    verifyNoMoreInteractions(this.userServiceMock);
  }

  @Test
  public void addUserView() throws Exception
  {
    this.mvcMoc.perform(get("/users/add")).andExpect(status().isOk()).andExpect(view().name("/user/addUser"));
    verifyNoMoreInteractions(this.userServiceMock);
  }

  @Test
  public void addUser() throws Exception
  {
    this.mvcMoc
        .perform(post("/users/add").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("fullName", "John Doe")
            .param("username", "johndoe").param("email", "test@test.com").param("password", "123456")
            .param("homeCountryForm.selectedKey", "USA").requestAttr("userForm", new UserForm()))
        .andExpect(status().isFound()).andExpect(view().name("redirect:/users"));

    ArgumentCaptor<UserForm> formObjectArgument = ArgumentCaptor.forClass(UserForm.class);
    verify(this.userServiceMock, times(1)).addUser(formObjectArgument.capture());
    verifyNoMoreInteractions(this.userServiceMock);
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
    this.mvcMoc.perform(post("/users/{id}/delete", 0L)).andExpect(status().isFound()).andExpect(view().name("redirect:/users"));
    verify(this.userServiceMock, times(1)).deleteUser(0L);
    verifyNoMoreInteractions(this.userServiceMock);
  }

  @Test
  public void updateUser() throws Exception
  {
    Long id = 0L;
    this.mvcMoc
        .perform(post("/users/{id}/edit", id).contentType(MediaType.APPLICATION_FORM_URLENCODED).param("fullName", "John Doe")
            .param("email", "test@test.com").param("homeCountryForm.selectedKey", "USA").requestAttr("userForm", new UserForm()))
        .andExpect(status().isFound()).andExpect(view().name("redirect:/users/" + id));

    ArgumentCaptor<UserForm> formObjectArgument = ArgumentCaptor.forClass(UserForm.class);
    ArgumentCaptor<Long> idObjectArgument = ArgumentCaptor.forClass(Long.class);
    verify(this.userServiceMock, times(1)).editUser(idObjectArgument.capture(), formObjectArgument.capture());
    verifyNoMoreInteractions(this.userServiceMock);
    UserForm formObject = formObjectArgument.getValue();
    // assertEquals("John Doe", formObject.getFullName());
    assertThat("John Doe", equalTo(formObject.getFullName()));
    assertEquals("test@test.com", formObject.getEmail());
    assertEquals(UserCountry.USA, formObject.getHomeCountry());
    assertEquals(id, idObjectArgument.getValue());
  }

}
