package com.huseynov.restaurant.cart.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private String totalAmount;
    private Long customerId;
    private List<CartItemResponse> items;
}
