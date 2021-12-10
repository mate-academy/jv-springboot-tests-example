package mate.academy.springboot.shoopingapptests.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.shoopingapptests.model.Product;
import mate.academy.springboot.shoopingapptests.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to) {
        return productRepository.findAllByPriceBetween(from, to);
    }

    @Override
    public List<String> getUppercaseTitles() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(Product::getTitle)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
