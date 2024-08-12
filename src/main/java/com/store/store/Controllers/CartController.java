package com.store.store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.store.Entities.CartEntity;
import com.store.store.Entities.ProductEntity;
import com.store.store.Repositories.CartRepository;
import com.store.store.Repositories.ProductRepository;
import com.store.store.User.UserRepository;
import com.store.store.User.User;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired 
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cart/")
    public ResponseEntity<?> getAllCarts() {
        List<CartEntity> carts = this.cartRepository.findAll();

        return ResponseEntity.ok(carts);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        CartEntity cart = this.cartRepository.findById(id).orElseThrow();
    
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error, no se encontró el carrito con el id: "+id);
    } 

    @GetMapping("/cart/products/{userId}")
    public ResponseEntity<?> getAllProductsOfUser(@PathVariable Long userId) {
        List<ProductEntity> products = this.productRepository.getAllProductsFromUser(userId);
        if(products != null) {

            return ResponseEntity.ok(products);
        }
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no hay productos asignados al usuario de id: "+userId);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addCart(@RequestBody CartEntity cartEntity) {
        if (cartEntity != null) {

            //add increment amount if someone add the same product more than once
//s.cartRepository.existsProductInUserCart(cartEntity.getProduct().getProductId(), cartEntity.getUser().getUserId()));


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

    @PutMapping("cart/update/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Long id, @RequestBody CartEntity newCart) {
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

    @DeleteMapping("/cart/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        CartEntity cartToDelete = this.cartRepository.findById(id).orElseThrow();

        if(cartToDelete != null) {
            this.cartRepository.delete(cartToDelete);
            return ResponseEntity.ok(cartToDelete);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el carrito con el id: "+id);
    }
}