package com.store.store.repository;

import com.store.store.entity.ProductEntity;
import com.store.store.entity.dto.CartDto;
import com.store.store.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.store.entity.CartEntity;
import com.store.store.entity.dto.ProductCartDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
    
    @Query(value = "SELECT amount FROM cart WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Long getAmountOfCart(Long userId, Long productId);

   // @Query(value = "SELECT cart_id FROM cart WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    //Long getCartId(Long userId, Long productId);

    @Query("SELECT c.cartId FROM CartEntity c WHERE c.user = ?1 AND c.product = ?2")
    Long getCartId(User userId, ProductEntity product);

    @Query("SELECT new com.store.store.entity.dto.CartDto(c.cartId, c.product, c.user, c.amount, c.createdAt, c.updatedAt) FROM CartEntity c WHERE c.cartId = ?1")
    CartDto getCartObjectById(Long cartId);

    @Query("SELECT new com.store.store.entity.dto.ProductCartDto(p, c.amount, c.cartId) FROM CartEntity  c JOIN c.product p JOIN c.user u WHERE u.userId = :userId")
    List<ProductCartDto> getAllProductsFromUser(@Param("userId") Long userId);



}
