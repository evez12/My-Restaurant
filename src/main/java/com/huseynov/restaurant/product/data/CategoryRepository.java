package com.huseynov.restaurant.product.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByName(String name);

    Optional<Category> findCategoriesByNameLike(String name);

    boolean existsCategoryByName(String name);

    Optional<Category> findCategoryById(Long id);

}
