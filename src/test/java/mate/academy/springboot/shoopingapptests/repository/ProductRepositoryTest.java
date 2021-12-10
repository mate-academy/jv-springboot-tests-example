package mate.academy.springboot.shoopingapptests.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.shoopingapptests.model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Sql("/scripts/init_three_products.sql")
    void shouldReturnProductPriceGreaterThan1200() {
        List<Product> actual = productRepository
                .findAllByPriceBetween(BigDecimal.valueOf(1200), BigDecimal.valueOf(Double.MAX_VALUE));
        Assert.assertEquals(1, actual.size());
    }

    @Test
    @Sql("/scripts/init_three_products.sql")
    void shouldReturnProductPriceGreaterThan100AndLessThan1300() {
        List<Product> actual = productRepository
                .findAllByPriceBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(1300));
        actual.forEach(System.out::println);
        Assert.assertEquals(2, actual.size());
    }
}
