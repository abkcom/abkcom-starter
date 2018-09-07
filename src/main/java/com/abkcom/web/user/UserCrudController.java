package com.abkcom.web.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abkcom.service.user.UserCrudService;
import com.abkcom.service.user.UserNotFoundException;

@Controller
@RequestMapping("/users")
public class UserCrudController
{
  @Autowired
  private UserCrudService userService;
  @Autowired
  private UserFormValidator validator;

  @InitBinder("userForm")
  protected void initBinder(WebDataBinder binder)
  {
    binder.setValidator(validator);
  }
  
  @GetMapping
  public String list(Model model)
  {
    model.addAttribute("users", userService.getAllUsers());
    return "/user/listUsers";
  }

  @GetMapping("/{id}")
  public String view(@PathVariable Long id, Model model)
  {
    model.addAttribute("user", userService.getUser(id));
    return "/user/viewUser";
  }
  
  @GetMapping("/add")
  public String addForm(@ModelAttribute UserForm userForm)
  {
    return "/user/addUser";
  }

  @PostMapping("/add")
  public String add(@Valid @ModelAttribute UserForm userForm, BindingResult result)
  {
    if (result.hasErrors())
    {
      return "/user/addUser";
    }
    userService.addUser(userForm);
    return "redirect:/users";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, @ModelAttribute UserForm userForm)
  {
    userForm.populateForm(userService.getUser(id));
    return "/user/editUser";
  }
  
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id, @Valid UserForm userForm, BindingResult result)
  {
    if (result.hasErrors())
    {
      return "/user/editUser";
    }
    userService.editUser(id, userForm);
    return "redirect:/users/"+id;
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id)
  {
    userService.deleteUser(id);
    return "redirect:/users";
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)
  public void exceptionHandler(UserNotFoundException e)
  {
    e.printStackTrace();
  }
}
