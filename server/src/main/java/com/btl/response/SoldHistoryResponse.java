package com.btl.response;

import lombok.Data;

import java.util.Date;
@Data
public class SoldHistoryResponse {

    private long customerId;
    private String customerName;
    private String customerAddress;

    private String customerPhone;

    private long batchId;

    private String sku;

    private Date soldDate;

    private long price;

    private long quantity;

    public Date warranty;


}
