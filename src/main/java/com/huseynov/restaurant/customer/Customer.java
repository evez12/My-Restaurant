package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.cart.Cart;
import com.huseynov.restaurant.order.Order;
import com.huseynov.restaurant.reservation.Reservation;
import com.huseynov.restaurant.shared.enums.Gender;
import com.huseynov.restaurant.shared.model.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;

    @NaturalId()
    String email;
    String password;

    @Column(name = "phone_number")
    String phoneNumber;
    String address;
    boolean enabled = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    Gender gender = Gender.UNDEFINED;

    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH
    })
    @JoinTable(name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles; // Customer's role is "CUSTOMER" by default

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Cart cart;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Reservation reservation;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders;
}
