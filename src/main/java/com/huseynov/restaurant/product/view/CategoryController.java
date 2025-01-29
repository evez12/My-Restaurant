package com.huseynov.restaurant.product.view;

import com.huseynov.restaurant.product.CategoryService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("CategoryController::createCategory request body {}", categoryDTO);

        CategoryDTO category = categoryService.createCategory(categoryDTO);

        ApiResponse<CategoryDTO> response = ApiResponse.<CategoryDTO>builder()
                .status("Category created successfully")
                .results(category)
                .build();

        log.info("CategoryController::createCategory response category, {}", category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    ResponseEntity<ApiResponse<List<CategoryDTO>>> getCategories() {
        log.info("CategoryController::getCategories request");

        List<CategoryDTO> categories = categoryService.getCategories();

        ApiResponse<List<CategoryDTO>> response = ApiResponse.<List<CategoryDTO>>builder()
                .status("All Categories found successfully")
                .results(categories)
                .build();

        log.info("CategoryController::getCategories response categories, {}", categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<CategoryDTO>> getCategory(@PathVariable Long id) {
        log.info("CategoryController::getCategoryById request id {}", id);

        CategoryDTO category = categoryService.getCategoryById(id);

        ApiResponse<CategoryDTO> response = ApiResponse.<CategoryDTO>builder()
                .status("Category found successfully")
                .results(category)
                .build();

        log.info("CategoryController::getCategoryById response category, {}", category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("CategoryController::updateCategory request id {}, body {}", id, categoryDTO);

        CategoryDTO category = categoryService.updateCategory(id, categoryDTO);

        ApiResponse<CategoryDTO> response = ApiResponse.<CategoryDTO>builder()
                .status("Category updated successfully")
                .results(category)
                .build();

        log.info("CategoryController::updateCategory response category, {}", category);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<CategoryDTO>> deleteCategory(@PathVariable Long id) {
        log.info("CategoryController::deleteCategory request id {}", id);

        CategoryDTO category = categoryService.deleteCategoryById(id);

        ApiResponse<CategoryDTO> response = ApiResponse.<CategoryDTO>builder()
                .status("Category deleted successfully")
                .results(category)
                .build();

        log.info("CategoryController::deleteCategory response category, {}", category);
        return ResponseEntity.ok(response);
    }


}
