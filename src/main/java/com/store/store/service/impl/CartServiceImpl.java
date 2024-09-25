package com.store.store.service.impl;

import com.store.store.entity.CartEntity;
import com.store.store.entity.ProductCart;
import com.store.store.entity.ProductEntity;
import com.store.store.repository.CartRepository;
import com.store.store.repository.ProductRepository;
import com.store.store.service.CartService;
import com.store.store.user.User;
import com.store.store.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getAllCarts() {
        List<CartEntity> carts = this.cartRepository.findAll();

        return ResponseEntity.ok(carts);
    }

    @Override
    public ResponseEntity<?> getCartById(Long id) {
        CartEntity cart = this.cartRepository.findById(id).orElseThrow();

        if(cart != null) {
            return ResponseEntity.ok(cart);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error, no se encontró el carrito con el id: "+id);
    }

    @Override
    public ResponseEntity<?> getAllProductsOfUser(Long userId) {
        List<ProductEntity> products = this.productRepository.getAllProductsFromUser(userId);

        List<ProductCart> productsCart = new ArrayList<>();

        if(products != null) {

            for(ProductEntity product : products) {
                Long amount = this.getAmountFromProductOfUser(userId, product.getProductId());

                Long cartId = this.getCartId(userId, product.getProductId());

                if(amount != null && cartId != null) {
                    ProductCart productCart = new ProductCart(product, amount, cartId);
                    productsCart.add(productCart);
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el valor de cantidad del userId: "+userId+", y el productId: "+product.getProductId());
                }

            }

            return ResponseEntity.ok(productsCart);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no hay productos asignados al usuario de id: "+userId);
    }

    @Override
    public ResponseEntity<?> updateAmountProduct(Long amount, Long cartId) {
        CartEntity cart = this.cartRepository.findById(cartId).orElseThrow();
        if(cart != null) {
            cart.setAmount(amount);
            this.cartRepository.save(cart);
            return ResponseEntity.ok(cart);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("actualizacion de cantidad fallida bajo el id: "+cartId);
    }

    @Override
    public Long getAmountFromProductOfUser(Long userId, Long productId) {
        Long amount = this.cartRepository.getAmountOfCart(userId, productId);

        return amount;
    }

    @Override
    public Long getCartId(Long userId, Long productId) {
        Long cartId = this.cartRepository.getCartId(userId, productId);

        return cartId;
    }

    @Override
    public ResponseEntity<?> addCart(CartEntity cartEntity) {
        if (cartEntity != null) {
            if(getAmountFromProductOfUser(cartEntity.getUser().getUserId(), cartEntity.getProduct().getProductId()) != null) {
                Long cartId = this.getCartId(cartEntity.getUser().getUserId(), cartEntity.getProduct().getProductId());

                Long newAmount = (this.getAmountFromProductOfUser(cartEntity.getUser().getUserId(), cartEntity.getProduct().getProductId())) + 1;

                return this.updateAmountProduct(newAmount, cartId);
            }

            Long productId = cartEntity.getProduct().getProductId();
            Long userId = cartEntity.getUser().getUserId();

            ProductEntity product = this.productRepository.findById(productId).orElseThrow();

            User user = this.userRepository.findById(userId).orElseThrow();

            if(product != null) {
                cartEntity.setProduct(product);
            }

            if(user != null) {
                cartEntity.setUser(user);
            }

            this.cartRepository.save(cartEntity);
            return ResponseEntity.ok(cartEntity);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error al añadir el carrito");
    }

    @Override
    public ResponseEntity<?> updateCart(Long id, CartEntity newCart) {
        CartEntity oldCart = this.cartRepository.findById(id).orElseThrow();

        if (oldCart != null) {

            Long productId = newCart.getProduct().getProductId();
            Long userId = newCart.getUser().getUserId();

            ProductEntity product = this.productRepository.findById(productId).orElseThrow();

            User user = this.userRepository.findById(userId).orElseThrow();

            oldCart.setAmount(newCart.getAmount());
            oldCart.setCreatedAt(newCart.getCreatedAt());
            oldCart.setUpdatedAt(newCart.getUpdatedAt());
            oldCart.setProduct(product);
            oldCart.setUser(user);

            this.cartRepository.save(oldCart);
            return ResponseEntity.ok(oldCart);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error al actualizar el carrito");
    }

    @Override
    public ResponseEntity<?> deleteCart(Long id) {
        CartEntity cartToDelete = this.cartRepository.findById(id).orElseThrow();

        if(cartToDelete != null) {
            this.cartRepository.delete(cartToDelete);
            return ResponseEntity.ok(cartToDelete);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el carrito con el id: "+id);
    }
}
