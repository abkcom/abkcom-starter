package com.abkcom.web.user;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abkcom.service.user.UserCrudService;
import com.abkcom.service.user.UserNotFoundException;
import com.abkcom.service.user.UserView;
import com.abkcom.web.AbstractController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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
    binder.setValidator(this.validator);
  }

  @GetMapping
  public String list(Model model)
  {
    model.addAttribute("users", this.userCrudService.getAllUsers());
    return "/user/listUsers";
  }

  @GetMapping("/{id}")
  public String view(@PathVariable Long id, Model model)
  {
    model.addAttribute("user", this.userCrudService.getUser(id));
    return "/user/viewUser";
  }

  @GetMapping(params = "id")
  public String findUser(@RequestParam long id, Model model, HttpServletRequest req)
  {
    Optional<UserView> user = this.userCrudService.findUser(id);
    if (user.isPresent())
    {
      model.addAttribute("user", user.get());
      return "/user/viewUser";
    }
    addPageMessage(req, "user.not.found");
    return "/user/listUsers";
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
    this.userCrudService.addUser(userForm);
    return "redirect:/users";
  }

  @GetMapping("/addUserError")
  public String addUserErrorView(@ModelAttribute UserForm userForm)
  {
    return "/user/addUserError";
  }

  @PostMapping("/addUserError")
  public String addUserError(@ModelAttribute UserForm userForm)
  {
    this.userCrudService.addUserError();
    return "redirect:/users";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, @ModelAttribute UserForm userForm)
  {
    userForm.populateForm(this.userCrudService.getUser(id));
    return "/user/editUser";
  }

  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id, @Valid UserForm userForm, BindingResult result)
  {
    if (result.hasErrors())
    {
      return "/user/editUser";
    }
    this.userCrudService.editUser(id, userForm);
    return "redirect:/users/" + id;
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id)
  {
    this.userCrudService.deleteUser(id);
    return "redirect:/users";
  }

  @GetMapping("/secret")
  public String secretPage(Model model)
  {
    this.userCrudService.secretPage();
    return "redirect:/users";
  }

  @GetMapping("/secret2")
  public String secretPage2(Model model, RedirectAttributes ra)
  {
    if (this.userCrudService.isSecretPageAllowed())
    {
      return "redirect:/users";
    }
    addPageError(ra, "err.security");
    return "redirect:/users";
  }

  @ExceptionHandler
  public String exceptionHandler(UserNotFoundException e, RedirectAttributes ra)
  {
    addPageError(ra, "user.not.found");
    return "redirect:/users";
  }
}
