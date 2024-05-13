package com.store.store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.store.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
