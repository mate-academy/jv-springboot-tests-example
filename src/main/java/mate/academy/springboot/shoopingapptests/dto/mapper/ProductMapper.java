package mate.academy.springboot.shoopingapptests.dto.mapper;

import mate.academy.springboot.shoopingapptests.dto.ProductRequestDto;
import mate.academy.springboot.shoopingapptests.dto.ProductResponseDto;
import mate.academy.springboot.shoopingapptests.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toResponseDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setTitle(product.getTitle());
        responseDto.setPrice(product.getPrice());
        return responseDto;
    }

    public Product toModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return product;
    }
}
