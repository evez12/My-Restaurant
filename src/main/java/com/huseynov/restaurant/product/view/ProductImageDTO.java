package com.huseynov.restaurant.product.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class ProductImageDTO {
    Long id;
    String fileName;
    String fileType;
    @Lob
    Blob image;

    @Override
    public String toString() {
        return "ProductImageDTO{" +
                ", id='" + id + '\'' +
                "fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                '}';
    }
}
