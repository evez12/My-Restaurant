package com.huseynov.restaurant.cart.data;

import com.huseynov.restaurant.cart.Cart;
import com.huseynov.restaurant.product.data.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long itemId;
    int quantity;
    String category;
    @Column(name = "unit_price")
    BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "total_price")
    BigDecimal totalPrice = BigDecimal.ZERO;

    //    @JsonIgnore
    @JoinColumn(name = "cart_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    Cart cart;

    @JoinColumn(name = "product_id")
    @OneToOne(fetch = FetchType.LAZY)
    Product product;

    public void setTotalPrice() {
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + itemId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
