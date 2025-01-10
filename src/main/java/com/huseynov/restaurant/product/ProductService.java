package com.huseynov.restaurant.product;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    ProductDTO getProductById(Long id);

    ProductDTO deleteProduct(Long id);

    List<ProductDTO> getProducts();

    List<ProductDTO> getProductsByCategoryName(String categoryName);
}
