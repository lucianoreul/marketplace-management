package com.lucianoreul.marketplace.service;

import com.lucianoreul.marketplace.dto.ProductRequest;
import com.lucianoreul.marketplace.dto.ProductResponse;
import com.lucianoreul.marketplace.entity.Product;
import com.lucianoreul.marketplace.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImage(request.getImage());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus());

        productRepository.save(product);

        return mapToResponse(product);
    }


    public List<ProductResponse> listProducts(String title, String status) {
        List<Product> products;

        if (title != null && status != null) {
            products = productRepository.findAll().stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .filter(p -> p.getStatus().equalsIgnoreCase(status))
                    .toList();
        } else if (title != null) {
            products = productRepository.findByTitleContainingIgnoreCase(title);
        } else if (status != null) {
            products = productRepository.findByStatusIgnoreCase(status);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse r = new ProductResponse();
        r.setId(product.getId());
        r.setTitle(product.getTitle());
        r.setDescription(product.getDescription());
        r.setPrice(product.getPrice());
        r.setImage(product.getImage());
        r.setCategory(product.getCategory());
        r.setStatus(product.getStatus());
        r.setCreatedAt(product.getCreatedAt());
        r.setUpdatedAt(product.getUpdatedAt());
        return r;
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImage(request.getImage());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus());

        productRepository.save(product);
        return mapToResponse(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        productRepository.delete(product);
    }
}
