package com.store.store.Controllers;

import java.util.List;

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

import com.store.store.Entities.ProductEntity;
import com.store.store.Helpers.HttpHelper;
import com.store.store.Repositories.ProductRepository;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    private HttpHelper httpHelper;

    public ProductController() {
        this.httpHelper = new HttpHelper();
    }

    
    @GetMapping(value = {"/products/", "/products"})
    public ResponseEntity<?> getAllProducts() {
        List<ProductEntity> products = this.productRepository.findAll();

        return this.httpHelper.getAllItemsResponse(products);
    }

     @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);

        return this.httpHelper.getItemByIdResponse(product, id);
    }

    @PostMapping(value = {"/products/add", "/products/add/"})
    public ResponseEntity<?> createProduct(@RequestBody ProductEntity product) {
        if(product != null) {
            this.productRepository.save(product);
        }

        return this.httpHelper.getPostResponse(product);
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<?> updateUpdate(@RequestBody ProductEntity newProduct, @PathVariable Long id) {

        ProductEntity oldProduct = this.productRepository.findById(id).orElse(null);
        
        if(oldProduct != null) {
            oldProduct.setProductName(newProduct.getProductName());
            oldProduct.setDescription(newProduct.getDescription());
            oldProduct.setCategory(newProduct.getCategory());
            oldProduct.setPrice(newProduct.getPrice());
            oldProduct.setStock(newProduct.getStock());

            this.productRepository.save(oldProduct);
        }

        return this.httpHelper.getPutResponse(oldProduct, id);
    }
    
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);

        if(product != null) {
            this.productRepository.delete(product);
        }

        return this.httpHelper.getDeleteResponse(product, id);
    }

    //filters
    @GetMapping("/products/filterByCategory")
    //get by category
    public ResponseEntity<?> getProductByCategory(@RequestParam String category) {
        List<ProductEntity> products = this.productRepository.findByCategory(category);

        return this.httpHelper.getItemsByCategoryResponse(products, category);
    }

    @GetMapping("/products/filterByCategoryLimit")
    //get by category
    public ResponseEntity<?> getProductByCategoryLimit(@RequestParam String category, @RequestParam int limit) {
        List<ProductEntity> products = this.productRepository.findByCategoryAndLimit(category, limit);

        return this.httpHelper.getItemsByCategoryResponse(products, category);
    }
    //get by price
    @GetMapping("/products/filterByPrice")
    public ResponseEntity<?> getProductByPrice(@RequestParam Double price) {

        List<ProductEntity> products = this.productRepository.findByPrice(price);

        return this.httpHelper.getItemsByPriceResponse(products, price);

    }

   //get by limit
   @GetMapping("/products/limit")
   public ResponseEntity<?> getProductsByLimit(@RequestParam Integer limitValue) {
       List<ProductEntity> products = this.productRepository.findByLimit(limitValue);
       return this.httpHelper.getItemByLimitResponse(products, limitValue);
   }

     //get by categories
     @GetMapping("/products/categories/")
     public ResponseEntity<?> getAllCategories() {
         List<String> products = this.productRepository.findAllCategories();
         return this.httpHelper.getAllCategoriesResponse(products);
     }

    //get by category endpoint: http://localhost:8080/products/category?category=electronica
    @GetMapping("/products/category")
    public ResponseEntity<?> getByCategory(@RequestParam String category) {
        List<ProductEntity> products = this.productRepository.findByCategory(category);
        return this.httpHelper.getByCategoryResponse(products, category);
    }
     

          //get by keyword
     @GetMapping("/products/search")  //http://localhost:8080/products/search?keyword=product example endpoint
     public ResponseEntity<?> getProductByProductName(@RequestParam String keyword) {
         List<ProductEntity> products = this.productRepository.findByProductName(keyword);
 
         return this.httpHelper.getItemsByKeyWordResponse(products, keyword);
     }



}
