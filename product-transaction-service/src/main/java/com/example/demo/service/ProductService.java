package com.example.demo.service;

import com.example.demo.dto.ProductPostDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.Product;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private InventoryRepository inventoryRepository;
    private CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProduct(String search) {
        if (!Objects.isNull(search)) {
            return productRepository.searchProducts(search);
        } else return Collections.emptyList();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(ProductPostDto dto) {
        Category category = categoryService.checkExistCategory(dto.categoryId());
        Product newProduct = Product.builder()
                .productName(dto.productName())
                .price(dto.price())
                .image(dto.image())
                .category(category)
                .build();
        return productRepository.save(newProduct);
    }

    public Inventory addInventory(Product newProduct, Integer currentQuantity) {
        return inventoryRepository.save(Inventory.builder().product(newProduct).currentQuantity(currentQuantity).build());
    }

    public Product updateProduct(ProductPostDto dto, String id) {
        Product savedProduct = Product.builder().id(id).productName(dto.productName()).price(dto.price()).build();
        return productRepository.save(savedProduct);
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }
}
