package com.coding.tech.productservice.controller;

import com.coding.tech.productservice.Repository.ProductRepository;
import com.coding.tech.productservice.dto.ProductRequest;
import com.coding.tech.productservice.dto.ProductResponse;
import com.coding.tech.productservice.model.Product;
import com.coding.tech.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

private final ProductService productService;

   /* public ProductController(ProductService productService) {
        this.productService = productService;
    }*/


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

   /* @PostMapping("/filter")
    public List<Product> filterByPrice(@RequestBody Product product){
        List<Product> productlist= productRepository.findAll();
        BigDecimal price= product.getPrice();
        List<Product> list=productlist.stream().filter(p-> p.getPrice().compareTo(price)>=0).collect(Collectors.toList());
        return list;
    }*/


}
