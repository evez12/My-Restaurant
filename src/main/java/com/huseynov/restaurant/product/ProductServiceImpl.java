package com.huseynov.restaurant.product;

import com.huseynov.restaurant.product.data.Category;
import com.huseynov.restaurant.product.data.CategoryRepository;
import com.huseynov.restaurant.product.data.Product;
import com.huseynov.restaurant.product.data.ProductRepository;
import com.huseynov.restaurant.product.view.CreateProductDTO;
import com.huseynov.restaurant.product.view.ProductDTO;
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
    public Product createProduct(CreateProductDTO productDTO) {
        log.info("ProductServiceImpl::createProduct execution started, new product: {}", productDTO);
        Product product;
        try {
            if (productRepository.existsProductByName(productDTO.getName())) { // check if the product exists
                log.error("Product with name {} already exists", productDTO.getName());
                throw new ExistsItemException("Product with name " + productDTO.getName() + " already exists");
            }

            Category category = categoryRepository
                    .findCategoryByName(productDTO.getCategoryName())
                    .orElseThrow(() -> new CustomNotFoundException("Category not found, name: " + productDTO.getCategoryName()));

            product = modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            product.setId(null);
            product = productRepository.save(product);
        } catch (ExistsItemException e) {
            log.error("Exception occurred while create new product, REASON exists product name; productName: {}, Exception message: {}"
                    , productDTO.getName(), e.getMessage());
            throw e;
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while create new product, REASON not found category name ; categoryName: {}, Exception message: {}"
                    , productDTO.getCategoryName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred create new product, Exception message: {}", e.getMessage());
            throw new ProductServiceException("Exception occurred while create new product, productName: " + productDTO.getName());
        }
        log.info("Product with name: {} created", product.getName());
        log.info("ProductServiceImpl::createProduct execution ended ");
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        log.info("ProductServiceImpl::updateProduct execution started");
        Product product;
        try {
            product = productRepository.findProductById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Product not found, with id: " + id));
            log.info("Product before update: {}", product);

            Category category = categoryRepository
                    .findCategoryByName(productDTO.getCategoryName())
                    .orElseThrow(() -> new CustomNotFoundException("Category not found, name: " + productDTO.getCategoryName()));

            product = modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            product.setId(id);
            product = productRepository.save(product);
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while update product, REASON not found product by id: {}, Exception message: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred update product by id: {}, Exception message: {}", id, e.getMessage());
            throw new ProductServiceException("Exception occurred while update product, productId: " + id);
        }
        log.info("Product updated after, {}", product);
        log.info("ProductServiceImpl::updateProduct execution ended");
        return product;
    }

    @Override
    public Product getProductById(Long id) {
        log.info("ProductServiceImpl::getProduct execution started, productId: {}", id);
        Product product;
        try {
            product = productRepository.findProductById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Product with id " + id + " not found"));
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while get product, REASON not found product by id: {}, Exception message: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred get product by id: {}, Exception message: {}", id, e.getMessage());
            throw new ProductServiceException("Exception occurred while fetch product from Database, productId: " + id);
        }
        log.info("ProductServiceImpl:getProductById execution ended, product: {}", product);
        return product;
    }

    @Override
    public ProductDTO convertEntityToDTO(Product product) {
        log.info("ProductServiceImpl:convertEntityToDTO execution started");
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        log.info("ProductServiceImpl:convertEntityToDTO execution ended");
        return productDTO;
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("ProductServiceImpl::deleteProduct execution started");
        try {
            Product product = productRepository.findProductById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Product with id " + id + " not found"));
            productRepository.delete(product);
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while delete product, REASON not found product by id: {}, Exception message: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred delete product by id: {}, Exception message: {}", id, e.getMessage());
            throw new ProductServiceException("Exception occurred while delete product from Database, productId: " + id);
        }
        log.info("ProductServiceImpl::deleteProduct execution ended");
    }

    @Override
    public List<ProductDTO> getProducts() {
        log.info("ProductServiceImpl::getProducts execution started");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            log.warn("No products found");
            return List.of();
        }
        log.info("ProductServiceImpl::getProducts execution started");
        return products
                .stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        log.info("ProductServiceImpl::getProductsByCategory execution started, category name: {}", categoryName);
        List<Product> products;
        try {
            Category category = categoryRepository.findCategoryByName(categoryName)
                    .orElseThrow(() -> new CustomNotFoundException("Category not found, with name: " + categoryName));

            products = productRepository.findProductsByCategory(category);

            if (products.isEmpty()) {
                log.warn("No products found for category: {}", category);
                return List.of();
            }
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while get products by category name, REASON not found category name: {}, Exception message: {}"
                    , categoryName, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred get products by category name: {}, Exception message: {}", categoryName, e.getMessage());
            throw new ProductServiceException("Exception occurred while get products by category name from Database, categoryName: " + categoryName);
        }
        log.info("OrderServiceImpl:getProductsByCategoryName execution ended, products: {}", products);
        return products
                .stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    @Override
    public Product getProductByName(String productName) {
        log.info("ProductServiceImpl::getProductsByName execution started, product name: {}", productName);
        Product product;
        try {
            product = productRepository.findProductByName(productName)
                    .orElseThrow(() -> new CustomNotFoundException("Category not found, with name: " + productName));
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while get product by name, REASON not found product name: {}, Exception message: {}"
                    , productName, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred get product by name: {}, Exception message: {}", productName, e.getMessage());
            throw new ProductServiceException("Exception occurred while get product by name from Database, productName: " + productName);
        }
        log.info("OrderServiceImpl:getProductsByNameName execution ended, product: {}", product);
        return product;
    }

    @Override
    public List<ProductDTO> getProductsByNameStartWith(String name) {
        log.info("ProductServiceImpl::getProductsByNameStartWith execution started, product name: {}", name);
        List<Product> products;
        try {
            products = productRepository.findProductsByNameStartsWith(name);
            if (products.isEmpty()) {
                log.warn("No products found for name: {}", name);
                return List.of();
            }
        } catch (Exception e) {
            log.error("Exception occurred get products by name start with: {}, Exception message: {}", name, e.getMessage());
            throw new ProductServiceException("Exception occurred while get products by name start with name: " + name + "from Database");
        }
        log.info("ProductServiceImpl::getProductsByNameStartWith execution ended, products: {}", products);
        return products
                .stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> searchProducts(String productOrCategoryName) {
        log.info("ProductServiceImpl::searchProducts execution started, product or category name: {}", productOrCategoryName);
        List<Product> products;
        try {
            products = productRepository.findProductsByNameStartsWithOrCategory_Name(productOrCategoryName, productOrCategoryName);
            if (products.isEmpty()) {
                log.warn("No products found for product or category name: {}", productOrCategoryName);
                return List.of();
            }
        } catch (Exception e) {
            log.error("Exception occurred get products by product or category name:  {}, Exception message: {}", productOrCategoryName, e.getMessage());
            throw new ProductServiceException("Exception occurred while get products by product or category name: " + productOrCategoryName + "from Database");
        }
        log.info("ProductServiceImpl::searchProducts execution ended, products: {}", products);
        return products
                .stream()
                .map(this::convertEntityToDTO)
                .toList();
    }


}
