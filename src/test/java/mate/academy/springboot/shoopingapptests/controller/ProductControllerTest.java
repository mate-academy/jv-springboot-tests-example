package mate.academy.springboot.shoopingapptests.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.shoopingapptests.dto.ProductRequestDto;
import mate.academy.springboot.shoopingapptests.model.Product;
import mate.academy.springboot.shoopingapptests.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldShowAllProducts() {
        List<Product> mockProducts = List.of(
                new Product(41L, "iPhone X", BigDecimal.valueOf(999)),
                new Product(42L, "iPhone 11", BigDecimal.valueOf(1199)),
                new Product(43L, "iPhone 13", BigDecimal.valueOf(1399))
        );
        Mockito.when(productService.findAll()).thenReturn(mockProducts);

        RestAssuredMockMvc
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].id", Matchers.equalTo(41))
                .body("[0].title", Matchers.equalTo("iPhone X"))
                .body("[0].price", Matchers.equalTo(999))
                .body("[1].id", Matchers.equalTo(42))
                .body("[1].title", Matchers.equalTo("iPhone 11"))
                .body("[1].price", Matchers.equalTo(1199))
                .body("[2].id", Matchers.equalTo(43))
                .body("[2].title", Matchers.equalTo("iPhone 13"))
                .body("[2].price", Matchers.equalTo(1399));
    }

    @Test
    void shouldReturnAllProductsWithPriceBetweenTwoValues() {
        Mockito.when(productService.findAllByPriceBetween(BigDecimal.valueOf(1000), BigDecimal.valueOf(1300)))
                .thenReturn(List.of(new Product(42L, "iPhone 11", BigDecimal.valueOf(1199))));

        RestAssuredMockMvc
                .given()
                .queryParam("from", BigDecimal.valueOf(1000))
                .queryParam("to", BigDecimal.valueOf(1300))
                .when()
                .get("/products/by-price")
                .then()
                .statusCode(200)
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(42))
                .body("[0].title", Matchers.equalTo("iPhone 11"))
                .body("[0].price", Matchers.equalTo(1199));
    }

    @Test
    void shouldCreateProduct() {
        Product productToSave = new Product("iPhone 12", BigDecimal.valueOf(1249));
        Mockito.when(productService.save(productToSave))
                .thenReturn(new Product(81L, "iPhone 12", BigDecimal.valueOf(1249)));
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(productToSave.getTitle(), productToSave.getPrice()))
                .when()
                .post("/products")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(81))
                .body("title", Matchers.equalTo("iPhone 12"))
                .body("price", Matchers.equalTo(1249));
    }
}
