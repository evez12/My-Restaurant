package com.huseynov.restaurant.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CartItemDTO {

    String productName;
    private Long itemId;
    private Long productId;
    private int quantity;
    private String category;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}
