package com.huseynov.restaurant.product;

import com.huseynov.restaurant.product.data.Category;
import com.huseynov.restaurant.product.data.CategoryRepository;
import com.huseynov.restaurant.product.view.CategoryDTO;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.exception.ExistsItemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        log.info("CategoryServiceImpl::createCategory execution started");
        if (categoryRepo.existsCategoryByName(categoryDTO.getName())) {
            log.warn("Category already exists, name: {}", categoryDTO.getName());
            throw new ExistsItemException("Category already exists, name: " + categoryDTO.getName());
        }
        categoryDTO.setId(null); // to avoid setting the id from the client
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepo.save(category);
        log.info("Saved category: {}", category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        log.info("CategoryServiceImpl::updateCategory execution");

        Category category = categoryRepo.findCategoryById(id)
                .orElseThrow(() -> new CustomNotFoundException("Category not found, with id: " + id));
        log.info("Category before update: {}", category);
        category.setName(categoryDTO.getName());
        log.info("Category updated: {}", category);

        return modelMapper.map(categoryRepo.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        log.info("CategoryServiceImpl::getCategory execution started");
        Category category = categoryRepo.findCategoryById(id)
                .orElseThrow(() -> new CustomNotFoundException("Category not found, id: " + id));
        log.info("Category found: {}", category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        log.info("CategoryServiceImpl::getCategories execution started");
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            log.warn("No categories found");
            return new ArrayList<>();
        }

        return categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    @Override
    public CategoryDTO deleteCategoryById(Long id) {
        log.info("CategoryServiceImpl::deleteCategoryById execution started");

        Category category = categoryRepo.findCategoryById(id)
                .orElseThrow(() -> new CustomNotFoundException("Category not found, id: " + id));
        categoryRepo.delete(category);
        log.info("Category deleted: {}", category);

        return modelMapper.map(category, CategoryDTO.class);
    }
}
