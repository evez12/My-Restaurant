package com.huseynov.restaurant.product;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByName(String name);

    @Override
    @NonNull
    @Query("SELECT p FROM Product p join fetch p.category")
    List<Product> findAll();

    @Query("SELECT p FROM Product p join fetch p.category WHERE p.id = :id")
    Optional<Product> findProductById(Long id);

    List<Product> findProductsByCategory(Category category);

    boolean existsProductByName(String name);
}
