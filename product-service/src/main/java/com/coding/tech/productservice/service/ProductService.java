package com.coding.tech.productservice.service;

import com.coding.tech.productservice.Repository.ProductRepository;
import com.coding.tech.productservice.dto.ProductRequest;
import com.coding.tech.productservice.dto.ProductResponse;
import com.coding.tech.productservice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest){
        Product product= Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product Id"+product.getId()+"Is Saved");

    }

    public List<ProductResponse> getAllProducts() {
        List<Product> response =  productRepository.findAll();
       // response.stream().map(product -> mapToProductResponse(product));
     return   response.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
