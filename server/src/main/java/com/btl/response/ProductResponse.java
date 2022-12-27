package com.btl.response;

import lombok.Data;

@Data
public class ProductResponse {

    private long productId;
    private String name;

    private String sku;

    private String info;

    private long slNhap;

    private long slXuat;

    private long giaNhap;

    private  long giaXuat;
}
