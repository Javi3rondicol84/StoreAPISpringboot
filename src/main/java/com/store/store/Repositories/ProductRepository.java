package com.store.store.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.store.store.Entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT * FROM product WHERE category = ?1", nativeQuery = true)
    List<ProductEntity> findByCategory(String category);

    @Query(value = "SELECT * FROM product WHERE category = ?1 LIMIT ?2", nativeQuery = true)
    List<ProductEntity> findByCategoryAndLimit(String category, int limit);

    @Query(value = "SELECT * FROM product WHERE price = ?1", nativeQuery = true)
    List<ProductEntity> findByPrice(Double price);

    @Query(value = "SELECT * FROM product GROUP BY category LIMIT :limitValue", nativeQuery = true)
    List<ProductEntity> findByLimit(@Param("limitValue") Integer limitValue);

    @Query(value = "SELECT DISTINCT category FROM product", nativeQuery = true)
    List<String> findAllCategories();

    @Query(value = "SELECT * FROM product WHERE product_name LIKE %:keyword%", nativeQuery = true)
    List<ProductEntity> findByProductName(@Param("keyword") String keyword);
}