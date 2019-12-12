package com.kevinCarlWalker.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.spring.web.paths.AbstractPathProvider;

@Profile("live")
public class AppConfig {

  // application.base.url is set in Dockerfile. Otherwise, the value is empty.
  @Value("${application.base.url:}")
  private String applicationBaseUrl = "";

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*");
      }
    };
  }

  class AbsolutePathProvider extends AbstractPathProvider {
    @Override
    protected String applicationPath() {
      return applicationBaseUrl;
    }

    @Override
    protected String getDocumentationPath() {
      return applicationBaseUrl;
    }
  }
}
