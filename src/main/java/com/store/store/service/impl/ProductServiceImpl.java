package com.store.store.service.impl;

import com.store.store.entity.ProductEntity;
import com.store.store.helper.GenericHttpHelper;
import com.store.store.helper.HttpHelper;
import com.store.store.helper.ProductHttpHelper;
import com.store.store.repository.ProductRepository;
import com.store.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductHttpHelper onlyProductHttpHelper;

    @Autowired
    private GenericHttpHelper<ProductEntity> productHttpHelper;

    @Override
    public ResponseEntity<?> getAllProducts() {
        List<ProductEntity> products = this.productRepository.findAll();

        return this.productHttpHelper.getAllItemsResponse(products, "productos");
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        Optional<ProductEntity> product = this.productRepository.findById(id);

        return this.productHttpHelper.getItemByIdResponse(product, id, "producto");
    }

    @Override
    public ResponseEntity<?> createProduct(ProductEntity product) {
        if (product != null) {
            this.productRepository.save(product);
        }

        return this.productHttpHelper.getPostResponse(product, "producto");
    }

    @Override
    public ResponseEntity<?> updateUpdate(ProductEntity newProduct, Long id) {
        Optional<ProductEntity> oldProduct = this.productRepository.findById(id);

        if (oldProduct.isPresent()) {
            ProductEntity product = oldProduct.get();
            product.setProductName(newProduct.getProductName());
            product.setDescription(newProduct.getDescription());
            product.setCategory(newProduct.getCategory());
            product.setPrice(newProduct.getPrice());
            product.setStock(newProduct.getStock());

            this.productRepository.save(product);
        }

        return this.productHttpHelper.getPutResponse(oldProduct, id, "producto");
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        Optional<ProductEntity> product = this.productRepository.findById(id);

        if (product.isPresent()) {
            this.productRepository.delete(product.get());
        }

        return this.productHttpHelper.getDeleteResponse(product, id, "producto");
    }

    @Override
    public ResponseEntity<?> getProductsByLimit(Integer limitValue) {
        List<ProductEntity> products = this.productRepository.findByLimit(limitValue);

        return this.productHttpHelper.getItemsByLimitResponse(products, limitValue, "productos");
    }


    @Override
    public ResponseEntity<?> getProductsByCategory(String category) {
        List<ProductEntity> products = this.productRepository.findByCategory(category);

        return this.onlyProductHttpHelper.getProductsByCategoryResponse(products, category);
    }

    @Override
    public ResponseEntity<?> getByCategory(String category) {
        List<ProductEntity> products = this.productRepository.findByCategory(category);

        return this.onlyProductHttpHelper.getByCategoryResponse(products, category);
    }


    @Override
    public ResponseEntity<?> getProductsByCategoryLimit(String category, int limit) {
        List<ProductEntity> products = this.productRepository.findByCategoryAndLimit(category, limit);

        return this.onlyProductHttpHelper.getProductsByCategoryResponse(products, category);
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<String> products = this.productRepository.findAllCategories();

        return this.onlyProductHttpHelper.getAllCategoriesResponse(products);
    }

    @Override
    public ResponseEntity<?> getProductByPrice(Double price) {
        List<ProductEntity> products = this.productRepository.findByPrice(price);

        return this.onlyProductHttpHelper.getProductsByPriceResponse(products, price);
    }

    @Override
    public ResponseEntity<?> getProductByProductName(String keyword) {
        List<ProductEntity> products = this.productRepository.findByProductName(keyword);
        return this.onlyProductHttpHelper.getProductsByProductNameResponse(products, keyword);
    }

    @Override
    public ResponseEntity<?> getProductsPaginated(String category, Long limit, Long offset) {
        List<ProductEntity> products = this.productRepository.findProductsPaginated(category, limit, offset);

        return this.onlyProductHttpHelper.getProductsPaginatedResponse(products, category, limit, offset);
    }
}
