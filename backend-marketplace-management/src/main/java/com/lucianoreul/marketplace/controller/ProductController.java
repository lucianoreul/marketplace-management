package com.lucianoreul.marketplace.controller;

import com.lucianoreul.marketplace.dto.ProductRequest;
import com.lucianoreul.marketplace.dto.ProductResponse;
import com.lucianoreul.marketplace.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest request) {
        try {
            ProductResponse response = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Product created successfully",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal server error",
                    "message", "An error occurred while creating the product"
            ));
        }
    }

    @GetMapping
    public ResponseEntity<?> listProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status) {
        List<ProductResponse> products = productService.listProducts(title, status);
        return ResponseEntity.ok(Map.of(
                "message", "Products retrieved successfully",
                "data", products
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        try {
            ProductResponse response = productService.updateProduct(id, request);
            return ResponseEntity.ok(Map.of(
                    "message", "Product updated successfully",
                    "data", response
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Product not found",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal server error",
                    "message", "An error occurred while updating the product"
            ));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Product deleted successfully"
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Product not found",
                    "message", e.getMessage()
            ));
        }
    }

}
