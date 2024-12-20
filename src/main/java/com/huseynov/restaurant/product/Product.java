package com.huseynov.restaurant.product;

import com.huseynov.restaurant.menu.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NaturalId
    String name;

    String description;
    int inventory;
    BigDecimal price = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ProductImage> productImages = new HashSet<>();

    public Product(String name, String description, int inventory, BigDecimal price, Category category) {
        this.name = name;
        this.description = description;
        this.inventory = inventory;
        this.price = price;
        this.category = category;
    }
}
