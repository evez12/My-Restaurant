package com.huseynov.restaurant.product;

import com.huseynov.restaurant.product.data.Product;
import com.huseynov.restaurant.product.view.CreateProductDTO;
import com.huseynov.restaurant.product.view.ProductDTO;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductDTO productDTO);

    Product updateProduct(Long id, ProductDTO productDTO);

    Product getProductById(Long id);

    ProductDTO convertEntityToDTO(Product product);

    void deleteProduct(Long id);

    List<ProductDTO> getProducts();

    List<ProductDTO> getProductsByCategoryName(String categoryName);

    Product getProductByName(String name);

    List<ProductDTO> getProductsByNameStartWith(String name);

    List<ProductDTO> searchProducts(String name);

}
