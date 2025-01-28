package com.huseynov.restaurant.cart.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CartDTO {

        private Long cartId;
        private BigDecimal totalAmount;
        private Long customerId;
        private Set<CartItemDTO> items;

        @Override
        public String toString() {
                return "CartDTO{" +
                        "id=" + cartId +
                        ", totalAmount=" + totalAmount +
                        '}';
        }
}
