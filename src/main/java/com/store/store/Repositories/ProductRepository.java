package com.store.store.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.store.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query(value = "SELECT * FROM product WHERE category = ?1", nativeQuery = true)
    List<Product> findByCategory(String category);

    @Query(value = "SELECT * FROM product WHERE price = ?1", nativeQuery = true)
    List<Product> findByPrice(Double price);
    
}
