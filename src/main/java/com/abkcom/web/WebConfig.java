package com.abkcom.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.abkcom.web.util.StringToMoneyConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

  @Override
  public void addFormatters(FormatterRegistry registry)
  {
    registry.addConverter(new StringToMoneyConverter());
  }
}