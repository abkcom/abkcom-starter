package com.abkcom.web.flowDemo;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.abkcom.web.AbstractController;

@Controller
@RequestMapping("/pay")
@SessionAttributes("makePaymentBean")
public class MakePaymentController extends AbstractController
{
  @ModelAttribute(binding = false)
  public MakePaymentBean makePaymentBean()
  {
    return new MakePaymentBean();
  }

  @InitBinder("amountForm")
  protected void amountFormInitBinder(WebDataBinder binder)
  {
    binder.setValidator(new AmountFormValidator());
  }

  @InitBinder("cardNumberForm")
  protected void cardNumberFormInitBinder(WebDataBinder binder)
  {
    binder.setValidator(new CardNumberFormValidator());
  }

  @GetMapping
  public String showAmountForm(Model model)
  {
    model.addAttribute(new AmountForm());
    return "/flowDemo/enterAmount";
  }

  @PostMapping
  public String enterAmount(MakePaymentBean makePaymentBean, @Valid AmountForm amountForm, BindingResult result)
  {
    if (result.hasErrors())
    {
      return "/flowDemo/enterAmount";
    }
    makePaymentBean.setAmount(amountForm.getAmount());
    return "redirect:/pay/enter-card-number";
  }

  @GetMapping("/enter-card-number")
  public String showCardNumberForm(Model model)
  {
    model.addAttribute(new CardNumberForm());
    return "/flowDemo/enterCardNumber";
  }

  @PostMapping("/enter-card-number")
  public String enterCardNumber(MakePaymentBean makePaymentBean, @Valid CardNumberForm cardNumberForm, BindingResult result)
  {
    if (result.hasErrors())
    {
      return "/flowDemo/enterCardNumber";
    }
    makePaymentBean.setCardNumber(cardNumberForm.getCardNumber());
    return "redirect:/pay/confirm";
  }

  @GetMapping("/confirm")
  public String showConfirmation()
  {
    return "/flowDemo/confirmation";
  }

  @PostMapping("/confirm")
  public String confirm(MakePaymentBean makePaymentBean)
  {
    // makePaymentService.pay(makePaymentBean);
    return "redirect:/pay/receipt";
  }

  @GetMapping("/receipt")
  public String showReceipt(SessionStatus sessionStatus)
  {
    sessionStatus.setComplete();
    return "/flowDemo/receipt";
  }
}
