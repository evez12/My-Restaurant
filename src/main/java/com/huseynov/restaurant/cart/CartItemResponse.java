package com.huseynov.restaurant.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private String productDescription;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    private int quantity;
    private String totalAmount;
}
