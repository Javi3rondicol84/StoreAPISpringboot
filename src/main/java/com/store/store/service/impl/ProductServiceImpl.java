package com.store.store.service.impl;

import com.store.store.entity.ProductEntity;
import com.store.store.helper.HttpHelper;
import com.store.store.repository.ProductRepository;
import com.store.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private HttpHelper httpHelper;

    @Override
    public ResponseEntity<?> getAllProducts() {
        List<ProductEntity> products = this.productRepository.findAll();

        return this.httpHelper.getAllItemsResponse(products);
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);

        return this.httpHelper.getItemByIdResponse(product, id);
    }

    @Override
    public ResponseEntity<?> createProduct(ProductEntity product) {
        if (product != null) {
            this.productRepository.save(product);
        }

        return this.httpHelper.getPostResponse(product);
    }

    @Override
    public ResponseEntity<?> updateUpdate(ProductEntity newProduct, Long id) {
        ProductEntity oldProduct = this.productRepository.findById(id).orElse(null);

        if (oldProduct != null) {
            oldProduct.setProductName(newProduct.getProductName());
            oldProduct.setDescription(newProduct.getDescription());
            oldProduct.setCategory(newProduct.getCategory());
            oldProduct.setPrice(newProduct.getPrice());
            oldProduct.setStock(newProduct.getStock());

            this.productRepository.save(oldProduct);
        }

        return this.httpHelper.getPutResponse(oldProduct, id);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);

        if (product != null) {
            this.productRepository.delete(product);
        }

        return this.httpHelper.getDeleteResponse(product, id);
    }

    @Override
    public ResponseEntity<?> getProductByCategory(String category) {
        List<ProductEntity> products = this.productRepository.findByCategory(category);

        return this.httpHelper.getItemsByCategoryResponse(products, category);
    }

    @Override
    public ResponseEntity<?> getProductByCategoryLimit(String category, int limit) {
        List<ProductEntity> products = this.productRepository.findByCategoryAndLimit(category, limit);

        return this.httpHelper.getItemsByCategoryResponse(products, category);
    }

    @Override
    public ResponseEntity<?> getProductByPrice(Double price) {
        List<ProductEntity> products = this.productRepository.findByPrice(price);

        return this.httpHelper.getItemsByPriceResponse(products, price);
    }

    @Override
    public ResponseEntity<?> getProductsByLimit(Integer limitValue) {
        List<ProductEntity> products = this.productRepository.findByLimit(limitValue);

        return this.httpHelper.getItemByLimitResponse(products, limitValue);
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<String> products = this.productRepository.findAllCategories();
        return this.httpHelper.getAllCategoriesResponse(products);
    }

    @Override
    public ResponseEntity<?> getByCategory(String category) {
        List<ProductEntity> products = this.productRepository.findByCategory(category);
        return this.httpHelper.getByCategoryResponse(products, category);
    }

    @Override
    public ResponseEntity<?> getProductByProductName(String keyword) {
        List<ProductEntity> products = this.productRepository.findByProductName(keyword);
        return this.httpHelper.getItemsByKeyWordResponse(products, keyword);
    }

    @Override
    public ResponseEntity<?> getProductsPaginated(String category, Long limit, Long offset) {
        List<ProductEntity> products = this.productRepository.findProductsPaginated(category, limit, offset);

        return ResponseEntity.ok(products);
    }
}
