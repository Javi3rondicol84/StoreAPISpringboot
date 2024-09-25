package com.store.store.controller;

import java.util.List;

import com.store.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.store.entity.ProductEntity;
import com.store.store.helper.HttpHelper;
import com.store.store.repository.ProductRepository;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = { "/products/", "/products" })
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping(value = { "/products/add", "/products/add/" })
    public ResponseEntity<?> createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<?> updateUpdate(@RequestBody ProductEntity newProduct, @PathVariable Long id) {
        return productService.updateUpdate(newProduct, id);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    // filters
    @GetMapping("/products/filterByCategory")
    // get by category
    public ResponseEntity<?> getProductByCategory(@RequestParam String category) {
        return productService.getProductByCategory(category);
    }

    @GetMapping("/products/filterByCategoryLimit")
    // get by category
    public ResponseEntity<?> getProductByCategoryLimit(@RequestParam String category, @RequestParam int limit) {
        return productService.getProductByCategoryLimit(category, limit);
    }

    // get by price
    @GetMapping("/products/filterByPrice")
    public ResponseEntity<?> getProductByPrice(@RequestParam Double price) {
        return productService.getProductByPrice(price);
    }

    // get by limit
    @GetMapping("/products/limit")
    public ResponseEntity<?> getProductsByLimit(@RequestParam Integer limitValue) {
        return productService.getProductsByLimit(limitValue);
    }

    // get by categories
    @GetMapping("/products/categories/")
    public ResponseEntity<?> getAllCategories() {
        return productService.getAllCategories();
    }

    // get by category endpoint:
    // http://localhost:8080/products/category?category=electronica
    @GetMapping("/products/category")
    public ResponseEntity<?> getByCategory(@RequestParam String category) {
        return productService.getByCategory(category);
    }

    // get by keyword
    @GetMapping("/products/search") // http://localhost:8080/products/search?keyword=product example endpoint
    public ResponseEntity<?> getProductByProductName(@RequestParam String keyword) {
        return productService.getProductByProductName(keyword);
    }

    //get by category pagination
    @GetMapping("/products/filterByCategoryPagination")
    public ResponseEntity<?> getProductsPaginated(@RequestParam String category, @RequestParam Long limit, @RequestParam Long offset) {
        return productService.getProductsPaginated(category, limit, offset);
    }

}
