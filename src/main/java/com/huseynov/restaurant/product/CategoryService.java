package com.huseynov.restaurant.product;

import com.huseynov.restaurant.product.view.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO deleteCategoryById(Long id);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getCategories();
}
