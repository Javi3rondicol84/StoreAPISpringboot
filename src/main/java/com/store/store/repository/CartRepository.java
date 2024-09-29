package com.store.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.store.entity.CartEntity;
import com.store.store.entity.dto.ProductCartDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
    
    @Query(value = "SELECT amount FROM cart WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Long getAmountOfCart(Long userId, Long productId);

    @Query(value = "SELECT cart_id FROM cart WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Long getCartId(Long userId, Long productId);

    @Query("SELECT new com.store.store.entity.dto.ProductCartDto(p, c.amount, c.cartId) FROM CartEntity  c JOIN c.product p WHERE c.user = :userId")
    List<ProductCartDto> getAllProductsFromUser(@Param("userId") Long userId);



}
