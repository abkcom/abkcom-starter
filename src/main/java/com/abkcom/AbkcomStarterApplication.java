package com.abkcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class AbkcomStarterApplication extends SpringBootServletInitializer
{

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
  {
    return application.sources(AbkcomStarterApplication.class);
  }

  @Bean
  public MessageSource messageSource()
  {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public LocalValidatorFactoryBean getValidator()
  {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  public static void main(String[] args)
  {
    SpringApplication.run(AbkcomStarterApplication.class, args);
  }
}
