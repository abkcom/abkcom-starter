package com.abkcom.web.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abkcom.service.user.UserCrudService;
import com.abkcom.service.user.UserNotFoundException;
import com.abkcom.web.AbstractController;

@Controller
@RequestMapping("/users")
public class UserCrudController extends AbstractController
{
  @Autowired
  private UserCrudService userCrudService;
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
    model.addAttribute("users", userCrudService.getAllUsers());
    return "/user/listUsers";
  }

  @GetMapping("/{id}")
  public String view(@PathVariable Long id, Model model)
  {
    model.addAttribute("user", userCrudService.getUser(id));
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
    userCrudService.addUser(userForm);
    return "redirect:/users";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, @ModelAttribute UserForm userForm)
  {
    userForm.populateForm(userCrudService.getUser(id));
    return "/user/editUser";
  }
  
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id, @Valid UserForm userForm, BindingResult result)
  {
    if (result.hasErrors())
    {
      return "/user/editUser";
    }
    userCrudService.editUser(id, userForm);
    return "redirect:/users/"+id;
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id)
  {
    userCrudService.deleteUser(id);
    return "redirect:/users";
  }

  @ExceptionHandler
  public String exceptionHandler(UserNotFoundException e, RedirectAttributes ra)
  {
    addPageError(ra, "user.not.found");
    return "redirect:/users";
  }
}
