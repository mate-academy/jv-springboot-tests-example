# SpringBoot testing

### Overview
This repo contains examples of integration and unit tests. 
You can find tests for the controller, repository, and service layers.
The business domain is straightforward and contains only one model: Product.

### Before you start
All tests should pass regardless of environment setup. 
But if you want to run the application locally, please open the `application.properties` and provide a valid MySQL username and password.

### Test classes
- **ProductRepositoryTest** - class for testing repository class. 
The Spring Data JPA library is used under the hood. 
There is no need to test standard CRUD methods or methods without `@Query` annotation.
The best practice in testing repository classes is to use the same DB. 
For this purpose `testcontainers` are used. 
In this case, there is no need to hardcode Datasource URL or port. They are fetched dynamically.
Also, pay attention to the `@Sql` annotation that allows you to specify the file path with SQL script. 
This script will be executed before the test method.

- **ProductControllerTest** - class for testing class annotated with `@RestController` annotation.
`restassured` library is used to verify the result of API response.

    NOTE: `ProductControllerTest` class is annotated with `@SpringBootTest` annotation which creates an application context. 
    In the `ProductControllerTest` class the only `ProductService` class behavior is mocked, but the "real" ProductMapper bean will be called during the test invokes.

- **ProductServiceImplTest** - class for testing service class. 
There are used following annotations:
    - `@Mock` - used to mock the `ProductRepository` behavior because we are not interested in testing repository behavior in this test class
    - `@InjectMocks` - used to create a `ProductServiceImpl` bean. All mocked beans will be injected into this object.
