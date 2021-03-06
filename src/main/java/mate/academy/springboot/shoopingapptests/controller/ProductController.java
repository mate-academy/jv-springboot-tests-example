package mate.academy.springboot.shoopingapptests.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.shoopingapptests.dto.ProductRequestDto;
import mate.academy.springboot.shoopingapptests.dto.ProductResponseDto;
import mate.academy.springboot.shoopingapptests.dto.mapper.ProductMapper;
import mate.academy.springboot.shoopingapptests.model.Product;
import mate.academy.springboot.shoopingapptests.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll().stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam BigDecimal from, @RequestParam BigDecimal to) {
        return productService.findAllByPriceBetween(from, to)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
