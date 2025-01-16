package com.huseynov.restaurant.product;

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
@NamedEntityGraph(name = "product-with-category-and-images",
        attributeNodes = {
                @NamedAttributeNode("category"),
                @NamedAttributeNode("productImages")
        }
)
@NamedEntityGraph(name = "product-with-category",
        attributeNodes = @NamedAttributeNode("category")
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NaturalId
    String name;

    String description;
    int inventory;
    BigDecimal price = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY
            , cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ProductImage> productImages = new HashSet<>();

    Product(String name, String description, int inventory, BigDecimal price, Category category) {
        this.name = name;
        this.description = description;
        this.inventory = inventory;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", inventory=" + inventory +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
