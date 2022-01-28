package com.abkcom.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abkcom.web.AbstractController;

@Controller
@RequestMapping("/link")
public class LinkController extends AbstractController
{
  @GetMapping
  public String showLink()
  {
    return "/test/link";
  }
}
