package com.kevinCarlWalker.web;

import com.kevinCarlWalker.web.DataAccess.DatabaseHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableSwagger2
@Import(AppConfig.class)
public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    LOGGER.info("Starting up Names Service");
    DatabaseHelpers.initializeDatabase();
    SpringApplication app = new SpringApplication(Application.class);
    app.setAdditionalProfiles("live");
    app.run(args);
  }
}
