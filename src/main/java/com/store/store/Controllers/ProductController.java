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
import org.springframework.web.bind.annotation.RestController;

import com.store.Helpers.HttpHelper;
import com.store.store.Entities.Product;
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
        List<Product> products = this.productRepository.findAll();

        return this.httpHelper.getAllItemsResponse(products);
    }

     @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = this.productRepository.findById(id).orElse(null);

        return this.httpHelper.getItemByIdResponse(product, id);
    }

    @PostMapping(value = {"/products/add", "/products/add/"})
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if(product != null) {
            this.productRepository.save(product);
        }

        return this.httpHelper.getPostResponse(product);
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<?> updateUpdate(@RequestBody Product newProduct, @PathVariable Long id) {

        Product oldProduct = this.productRepository.findById(id).orElse(null);
        
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
    public ResponseEntity<?> deleteCalendar(@PathVariable Long id) {
        Product product = this.productRepository.findById(id).orElse(null);

        if(product != null) {
            this.productRepository.delete(product);
        }

        return this.httpHelper.getDeleteResponse(product, id);
    }

    //filtros

    @GetMapping("/products/{category}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category) {
        //Product product = this.productRepository.findById().orElse(null);
      
        return null;
        //return this.httpHelper.getItemByIdResponse(product, id);
    }
    

}
