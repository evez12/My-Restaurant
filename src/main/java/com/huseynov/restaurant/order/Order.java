package com.huseynov.restaurant.order;

import com.huseynov.restaurant.customer.data.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraph(name = "order-with-items-and-customer",
        attributeNodes = {
                @NamedAttributeNode(value = "orderItems", subgraph = "items-with-product"),
        },
        subgraphs = @NamedSubgraph(
                name = "items-with-product",
                attributeNodes = @NamedAttributeNode(value = "product")
        )
)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name = "created_date")
    LocalDateTime createdDate;

    @Column(name = "total_amount")
    BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    Set<OrderItem> orderItems = new HashSet<>();

    @Override
    public String toString() {
        return "Order{" +
                "createdDate=" + createdDate +
                ", id=" + id +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                '}';
    }
}
