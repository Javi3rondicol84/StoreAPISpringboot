package com.store.store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.store.Entities.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
    
    @Query(value = "SELECT amount FROM cart WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Long getAmountOfCart(Long userId, Long productId);

    @Query(value = "SELECT cart_id FROM cart WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Long getCartId(Long userId, Long productId);

}
