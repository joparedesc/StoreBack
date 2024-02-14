package com.store.crudProducto.repository;

import com.store.crudProducto.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
    public String name = null;

    boolean existsByName(String name);

    boolean existsByPrice(int price);

    Optional<Product> findByName(String name);

    Product findByPrice(int price);

}
