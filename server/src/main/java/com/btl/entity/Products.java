package com.btl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productSku;
    @Column(nullable = false, length = 200)
    private String productName;
    private float productPrice;
    private float productWeight;
    private String productImg;
    private int productCategoryId;
    private Date productMfg; // ngày sản xuất
    private float productStock;
    private String productLocationNameId; // Nơi lưu sp (Cửa hàng, Bảo hành ...)
    private int productLocationId; // Id của nơi lưu sản phẩm (của hàng 1, của hàng 2 ...)

    @ManyToOne
    private Stored location;



}
