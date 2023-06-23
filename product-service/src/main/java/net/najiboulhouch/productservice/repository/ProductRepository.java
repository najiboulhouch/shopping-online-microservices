package net.najiboulhouch.productservice.repository;

import net.najiboulhouch.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product , String> {
}
