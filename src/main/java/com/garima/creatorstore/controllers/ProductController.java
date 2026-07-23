package com.garima.creatorstore.controllers;

import com.garima.creatorstore.entities.Product;
import com.garima.creatorstore.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable Long id){
        return productService.getProducts(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
         productService.deleteProduct(id);
         return "Deleted productid: "+id;
    }
}
