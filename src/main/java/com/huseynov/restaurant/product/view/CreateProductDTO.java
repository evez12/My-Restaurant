package com.huseynov.restaurant.product.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CreateProductDTO {
    Long id;

    @NotBlank(message = "Product name is required")
    String name;

    @NotBlank(message = "Product description is required")
    String description;

    @NotNull(message = "Product price is required")
    @Min(value = 0, message = "Price must be greater than 0")
    @Max(value = 1000000000, message = "Price must be less than 1000000000")
    BigDecimal price = BigDecimal.ZERO;


    @NotNull(message = "Inventory is required")
    @Min(value = 1, message = "Inventory must be greater than 0")
    @Max(value = 100000, message = "Inventory must be less than 100000")
    int inventory;

    @NotBlank(message = "Product image is required")
    String categoryName;

    Set<ProductImageDTO> productImage;

    @Override
    public String toString() {
        return "CreateProductDTO{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                "categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
