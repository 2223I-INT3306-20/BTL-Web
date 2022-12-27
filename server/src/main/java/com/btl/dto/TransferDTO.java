package com.btl.dto;

import lombok.Data;

@Data
public class TransferDTO {

    //private String sku;
    private long productId;
    private long toId;
    private long quantity;
}
