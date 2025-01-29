package com.huseynov.restaurant.order;

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

@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int quantity;
    BigDecimal price = BigDecimal.ZERO;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    Product product;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id")
    Order order;

    public OrderItem(Order order, BigDecimal price, Product product, int quantity) {
        this.order = order;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }
}
