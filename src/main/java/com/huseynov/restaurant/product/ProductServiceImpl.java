package com.huseynov.restaurant.product;

import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.exception.ExistsItemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        log.info("ProductServiceImpl::createProduct executed ");

        if (productRepository.existsProductByName(productDTO.getName())) { // check if the product exists
            log.error("Product with name {} already exists", productDTO.getName());
            throw new ExistsItemException("Product with name " + productDTO.getName() + " already exists");
        }

        Category category = categoryRepository
                .findCategoryByName(productDTO.getCategoryName())
                .orElseThrow(() -> new CustomNotFoundException("Category not found, name: " + productDTO.getCategoryName()));

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setId(null);
        product = productRepository.save(product);
        log.info("Product with id {} created", product);

        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        log.info("ProductServiceImpl::updateProduct executed");

        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new CustomNotFoundException("Product not found, with id: " + id));
        log.info("Product before update: {}", product);

        Category category = categoryRepository
                .findCategoryByName(productDTO.getCategoryName())
                .orElseThrow(() -> new CustomNotFoundException("Category not found, with name: " + productDTO.getCategoryName()));

        product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setId(id);
        product = productRepository.save(product);

        log.info("Product updated after, {}", product);

        return product;
    }

    @Override
    public Product getProductById(Long id) {
        log.info("ProductServiceImpl::getProduct executed ");

        return productRepository.findProductById(id)
                .orElseThrow(() -> new CustomNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public Product deleteProduct(Long id) {
        log.info("ProductServiceImpl::deleteProduct executed");
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new CustomNotFoundException("Product with id " + id + " not found"));
        productRepository.delete(product);
        return product;
    }

    @Override
    public List<ProductDTO> getProducts() {
        log.info("ProductServiceImpl::getProducts executed");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            log.warn("No products found");
            return List.of();
        }
        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        log.info("ProductServiceImpl::getProductsByCategory executed");

        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new CustomNotFoundException("Category not found, with name: " + categoryName));

        List<Product> products = productRepository.findProductsByCategory(category);

        if (products.isEmpty()) {
            log.warn("No products found for category: {}", category);
            return List.of();
        }

        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }
}
