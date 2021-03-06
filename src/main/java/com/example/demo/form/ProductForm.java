package com.example.demo.form;

import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private MultipartFile fileData;
    private boolean newProduct = false;

    public ProductForm(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
    }

}
