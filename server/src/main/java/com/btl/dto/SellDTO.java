package com.btl.dto;

import lombok.Data;

@Data
public class SellDTO {
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private long productId;
    private long price;
    private long quantity;
    private int warranty;
}
