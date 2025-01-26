package com.huseynov.restaurant.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customer")
@NamedEntityGraph(
        name = "customer-role",
        attributeNodes = @NamedAttributeNode("roles")

)
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

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    //    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Reservation reservation;

    //    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Order> orders;

    public Customer(Long id, String email, String password, Set<Role> roles) {
        this(id, email, password);
        this.roles = roles;
    }

    public Customer(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }


    public Customer(Long id, String name, String surname, String email, String password,
                    boolean enabled, String phoneNumber, String address, Gender gender, Role role) {
        this(id, email, password);
        this.name = name;
        this.surname = surname;
        this.enabled = enabled;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.roles = addRole(role);
    }

    public Customer(Long id, String name, String surname, String email, String password,
                    boolean enabled, String phoneNumber, String address, Gender gender) {

        this(id, email, password);
        this.name = name;
        this.surname = surname;
        this.enabled = enabled;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
    }

    private Set<Role> addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }

        this.roles.add(role);
        return this.roles;
    }

    public boolean isDisabled() {
        log.warn("Customer::isDisabled called");
        return !enabled;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
