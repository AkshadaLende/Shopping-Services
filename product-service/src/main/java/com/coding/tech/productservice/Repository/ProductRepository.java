package com.coding.tech.productservice.Repository;

import com.coding.tech.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ProductRepository  extends MongoRepository<Product,String> {

}