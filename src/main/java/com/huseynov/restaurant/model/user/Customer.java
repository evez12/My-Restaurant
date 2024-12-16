package com.huseynov.restaurant.model.user;

import com.huseynov.restaurant.enums.Gender;
import com.huseynov.restaurant.model.Role;
import com.huseynov.restaurant.model.cart.Cart;
import com.huseynov.restaurant.model.order.Order;
import com.huseynov.restaurant.model.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.util.List;

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
    boolean enabled;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Enumerated(EnumType.STRING)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "role_id")
    Role role; // Customer's role is "CUSTOMER" (always)

//    {
//        role.setId(1L);
//    }

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Cart cart;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Reservation reservation;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders;
}
