package com.btl.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class ProductDTO {
    private String productSku;
    private String productName;
    private String productPrice;
    private String productWeight;
    private String productImg;
    private int productCategoryId;
    @Temporal(TemporalType.DATE)
    private Date productMfg; // ngày sản xuất

    private long optionId;
}
