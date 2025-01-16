package com.huseynov.restaurant.product;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(value = "product-with-category", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Product> findProductByName(String name);

    @Override
    @NonNull
//    @EntityGraph(value = "product-with-category", type = EntityGraph.EntityGraphType.FETCH)
    List<Product> findAll();

    @EntityGraph(value = "product-with-category", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Product> findProductById(Long id);

    List<Product> findProductsByCategory(Category category);

    boolean existsProductByName(String name);
}
