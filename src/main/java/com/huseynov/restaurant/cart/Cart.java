package com.huseynov.restaurant.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huseynov.restaurant.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "cart")
@NamedEntityGraph(name = "cart-with-items-and-product",
        attributeNodes = @NamedAttributeNode(
                value = "items", subgraph = "items-with-product"
        ),
        subgraphs = {
                @NamedSubgraph(
                        name = "items-with-product",
                        attributeNodes = @NamedAttributeNode(value = "product", subgraph = "product-with-category")
                ),
                @NamedSubgraph(
                        name = "product-with-category",
                        attributeNodes = @NamedAttributeNode("category")
                )
        }
)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long cartId;

    @Column(name = "total_amount")
    BigDecimal totalAmount = BigDecimal.ZERO;

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "cart",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    Set<CartItem> items = new HashSet<>();

    public void addItem(CartItem cartItem) {
        this.items.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem cartItem) {
        this.items.remove(cartItem);
        cartItem.setCart(null);
        updateTotalAmount();
    }

    public void updateItem(CartItem cartItem, int quantity) {
        this.items.stream()
                .filter(item -> item.getProduct().getId().equals(cartItem.getProduct().getId()))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(cartItem.getUnitPrice());
                    item.setTotalPrice();
                });
        cartItem.setCart(this);
        updateTotalAmount();
    }

    public void clearCart() {
        this.items.clear();
        this.totalAmount = BigDecimal.ZERO;
    }

    private void updateTotalAmount() {
        this.totalAmount = items
                .stream()
                .map(item -> {
                    BigDecimal unitPrice = item.getUnitPrice();
                    if (unitPrice == null) {
                        return BigDecimal.ZERO;
                    }
                    return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + cartId +
                ", totalAmount=" + totalAmount +
                ", cartItems=" + items +
                '}';
    }
}
