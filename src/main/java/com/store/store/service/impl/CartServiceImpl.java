package com.store.store.service.impl;

import com.store.store.entity.CartEntity;
import com.store.store.entity.dto.ProductCartDto;
import com.store.store.entity.ProductEntity;
import com.store.store.helper.GenericHttpHelper;
import com.store.store.repository.CartRepository;
import com.store.store.repository.ProductRepository;
import com.store.store.service.CartService;
import com.store.store.user.User;
import com.store.store.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenericHttpHelper<CartEntity> cartGenericHttpHelper;

    //ERROR Usuario en carrito deja de funcionar, ocurre cuando por error
    // un usuario tiene el mismo producto 2 veces. REVISAR AÑADIR PRODUCTO A CARRITO DE USUARIO


    @Override
    public ResponseEntity<?> getAllCarts() {
        List<CartEntity> carts = this.cartRepository.findAll();

        return cartGenericHttpHelper.getAllItemsResponse(carts, "carritos");
    }

    @Override
    public ResponseEntity<?> getCartById(Long id) {
        Optional<CartEntity> cart = this.cartRepository.findById(id);

        return cartGenericHttpHelper.getItemByIdResponse(cart, id, "carrito");
    }

    @Override
    public ResponseEntity<?> getAllProductsOfUser(Long userId) {
        List<ProductCartDto> productsCart = this.cartRepository.getAllProductsFromUser(userId);

        if(productsCart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no hay productos asignados al usuario de id: "+userId);

        }

        return ResponseEntity.ok(productsCart);
    }

    @Override
    public ResponseEntity<?> updateAmountProduct(Long amount, Long cartId) {
        Optional<CartEntity> cart = this.cartRepository.findById(cartId);

        if(!cart.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no existe un carrito bajo el id: "+cartId);
        }
        else if(amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cantidad invalida");
        }
        else {
            CartEntity cartEntity = cart.get();
            cartEntity.setAmount(amount);
            this.cartRepository.save(cartEntity);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartEntity);
        }
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

        if(cartEntity == null || cartEntity.getProduct() == null || cartEntity.getUser() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("carrito incompleto, fallido");
        }

        Long cartId = this.cartRepository.getCartId(cartEntity.getUser().getUserId(), cartEntity.getProduct().getProductId());

        if(cartId == null) {
            this.cartRepository.save(cartEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartEntity);
        }

        CartEntity cartUpdated = this.cartRepository.findById(cartId).get();

        cartUpdated.setAmount(cartUpdated.getAmount() + cartEntity.getAmount());

        this.cartRepository.save(cartUpdated);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartUpdated);
    }

    @Override
    public ResponseEntity<?> updateCart(Long id, CartEntity newCart) {
        Optional<CartEntity> oldCart = this.cartRepository.findById(id);

        if(!oldCart.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error al actualizar el carrito");
        }

        if(newCart == null || newCart.getProduct() == null || newCart.getUser() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("carrito incompleto, fallido");
        }

        CartEntity cartEntity = oldCart.get();

        System.out.println(cartEntity);

        ProductEntity product = this.productRepository.findById(newCart.getProduct().getProductId()).get();
        User user = this.userRepository.findById(newCart.getUser().getUserId()).get();

        cartEntity.setAmount(newCart.getAmount());
        cartEntity.setCreatedAt(cartEntity.getCreatedAt());
        cartEntity.setUpdatedAt(newCart.getUpdatedAt());
        cartEntity.setProduct(product);
        cartEntity.setUser(user);

        this.cartRepository.save(cartEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartEntity);
    }

    @Override
    public ResponseEntity<?> deleteCart(Long id) {
        Optional<CartEntity> cartToDelete = this.cartRepository.findById(id);

        if(!cartToDelete.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el carrito con el id: "+id);
        }

        this.cartRepository.delete(cartToDelete.get());
        return ResponseEntity.ok(cartToDelete);
    }
}
