package com.btl.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productSKU;
    private String productName;
    private float productPrice;
    private float productWeight;
    private String imgLocation;
    private int productCategoryId;
    private Date productMFG;
    private float stock;
    private String locationName; // Nơi lưu sp (Cửa hàng, Bảo hành ...)
    private int locationId; // Id của nơi lưu sản phẩm (của hàng 1, của hàng 2 ...)



}
