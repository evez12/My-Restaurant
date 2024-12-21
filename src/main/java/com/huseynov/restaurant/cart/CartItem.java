package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.product.Product;
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
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int quantity;

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
}
