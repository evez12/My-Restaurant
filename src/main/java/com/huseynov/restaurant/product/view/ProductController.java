package com.huseynov.restaurant.product.view;

import com.huseynov.restaurant.product.ProductService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        log.info("ProductController::createProduct execution started, request body: {}", productDTO);

        ProductDTO product = productService
                .convertEntityToDTO(productService.createProduct(productDTO));

        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product created successfully")
                .results(product)
                .build();

        log.info("ProductController:createProduct execution ended, response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        log.info("ProductController::getProductById execution started, id: {}", id);

        ProductDTO product = productService
                .convertEntityToDTO(productService.getProductById(id));
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product found successfully")
                .results(product)
                .build();
        log.info("ProductController:getProductById execution ended, response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductByName(@PathVariable(name = "name") String name) {
        log.info("ProductController::getProductByName execution started, name: {}", name);

        ProductDTO product = productService.convertEntityToDTO(productService.getProductByName(name));
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product found successfully")
                .results(product)
                .build();
        log.info("ProductController::getProductByName execution ended, response: {}", response);
        return ResponseEntity.ok(response);
    }

    // search product by name or category name
    @GetMapping("/search/{name}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> searchProduct(@PathVariable(name = "name") String productOrCategoryName) {
        log.info("ProductController::searchProduct execution started, search: {}", productOrCategoryName);

        List<ProductDTO> products = productService.searchProducts(productOrCategoryName);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .status("Products found successfully")
                .results(products)
                .build();
        log.info("ProductController::searchProduct execution ended, response: {}", response);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/category/{categoryName}")
    ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(@PathVariable String categoryName) {
        log.info("ProductController::getProductsByCategory execution started, category name: {}", categoryName);

        List<ProductDTO> products = productService.getProductsByCategoryName(categoryName);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .status("Products found successfully")
                .results(products)
                .build();

        log.info("ProductController::getProductsByCategory execution ended response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    ResponseEntity<ApiResponse<List<ProductDTO>>> getProducts() {
        log.info("ProductController::getProducts execution started ");

        List<ProductDTO> products = productService.getProducts();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .status("All Products found successfully")
                .results(products)
                .build();

        log.info("ProductController::getProducts execution ended, response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/id/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long id) {
        log.info("ProductController::updateProduct execution started, request body: {}, id: {}", productDTO, id);

        ProductDTO product = productService
                .convertEntityToDTO(productService.updateProduct(id, productDTO));
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product updated successfully")
                .results(product)
                .build();

        log.info("ProductController::updateProduct execution ended response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> deleteProduct(@PathVariable Long id) {
        log.info("ProductController::deleteProduct execution started, id: {}", id);

        productService.deleteProduct(id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product deleted successfully, productId: " + id)
                .results(null)
                .build();

        log.info("ProductController::deleteProduct execution ended response: {}", response);
        return ResponseEntity.ok(response);
    }
}
