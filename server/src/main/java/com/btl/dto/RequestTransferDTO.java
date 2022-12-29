package com.btl.dto;

import lombok.Data;

@Data
public class RequestTransferDTO {
    private long productId;

    private long quantity;

    private long factoryId;

    private long price;
}
