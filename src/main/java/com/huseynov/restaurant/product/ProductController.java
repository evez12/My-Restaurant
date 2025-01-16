package com.huseynov.restaurant.product;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping("/products")
    ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("ProductController::createProduct executed, request body: {}", productDTO);

        ProductDTO product = modelMapper
                .map(productService.createProduct(productDTO), ProductDTO.class);

        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product created successfully")
                .results(product)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        log.info("ProductController::getProductById executed, id: {}", id);

        ProductDTO product = modelMapper
                .map(productService.getProductById(id), ProductDTO.class);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product found successfully")
                .results(product)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    ResponseEntity<ApiResponse<List<ProductDTO>>> getProducts() {
        log.info("ProductController::getProducts executed ");

        List<ProductDTO> products = productService.getProducts();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .status("All Products found successfully")
                .results(products)
                .build();

        log.info("ProductController::getProducts response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long id) {
        log.info("ProductController::updateProduct executed, request body: {}, id: {}", productDTO, id);

        ProductDTO product = modelMapper
                .map(productService.updateProduct(id, productDTO), ProductDTO.class);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product updated successfully")
                .results(product)
                .build();

        log.info("ProductController::updateProduct response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> deleteProduct(@PathVariable Long id) {
        log.info("ProductController::deleteProduct executed ");

        ProductDTO productDTO = modelMapper
                .map(productService.deleteProduct(id), ProductDTO.class);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("Product deleted successfully")
                .results(productDTO)
                .build();

        log.info("ProductController::deleteProduct response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/category/{categoryName}")
    ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(@PathVariable String categoryName) {
        log.info("ProductController::getProductsByCategory executed ");

        List<ProductDTO> products = productService.getProductsByCategoryName(categoryName);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .status("Products found successfully")
                .results(products)
                .build();

        log.info("ProductController::getProductsByCategory response: {}", response);
        return ResponseEntity.ok(response);
    }

}
