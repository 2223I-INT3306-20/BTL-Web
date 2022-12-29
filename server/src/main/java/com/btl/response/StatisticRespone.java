package com.btl.response;

import lombok.Data;

@Data
public class StatisticRespone {
    private String name;
    private String sku;
    private String info;
    private long quantity;
    private long price;
}
