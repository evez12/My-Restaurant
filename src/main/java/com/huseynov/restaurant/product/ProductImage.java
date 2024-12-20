package com.huseynov.restaurant.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_type")
    String fileType;

    @Lob
    Blob image;

    //    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "product_id")
    Product product;
}
