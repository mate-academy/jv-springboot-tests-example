package mate.academy.springboot.shoopingapptests.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.shoopingapptests.model.Product;
import mate.academy.springboot.shoopingapptests.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void shouldReturnValidUppercaseTitles() {
        Mockito.when(productRepository.findAll()).thenReturn(List.of(new Product("iPhone X", BigDecimal.valueOf(999))));

        List<String> actual = productService.getUppercaseTitles();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("IPHONE X", actual.get(0));
    }
}
