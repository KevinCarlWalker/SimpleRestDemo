# Simple Rest Demo
a REST service that hosts CRUD endpoints for creating / maintaining first name, last name pairings

# Swagger
This application uses swagger-ui to document its endpoints. This documentation is deployed along side the application and is found at {app-root}/swagger-ui.html.
 When running locally, the swagger documentation can be found at: http://localhost:8080/swagger-ui.html 

# Actuator
Spring boot actuator auto deploys several useful endpoints. These endpoints are exposed through the management port.  
When running locally, an example of actuator endpoint: http://localhost:8089/metrics

[Actuator endpoint documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html "Production Ready Features")

# H2 Database
This application uses a embedded, in memmory database created by H2. 

[H2 Database Engine](https://www.h2database.com/html/main.html "H2 landing page")

# To run locally
Run Main() found in Application.java
