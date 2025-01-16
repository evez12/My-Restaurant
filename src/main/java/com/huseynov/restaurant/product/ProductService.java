package com.huseynov.restaurant.product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);

    Product updateProduct(Long id, ProductDTO productDTO);

    Product getProductById(Long id);

    Product deleteProduct(Long id);

    List<ProductDTO> getProducts();

    List<ProductDTO> getProductsByCategoryName(String categoryName);
}
