package com.marketplace.productservice.service;

import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    public boolean checkAvailability(Long productId, int quantity) {
        Optional<Product> product = repository.findById(productId);
        return product.isPresent() && product.get().getQuantity() >= quantity;
    }

    public void updateQuantity(Long productId, int quantity) {
        Optional<Product> productOpt = repository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(product.getQuantity() - quantity);
            repository.save(product);
        }
    }
}