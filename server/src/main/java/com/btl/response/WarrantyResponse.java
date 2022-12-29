package com.btl.response;

import lombok.Data;

import java.util.Date;

@Data
public class WarrantyResponse {
    private Date receiveDate;

    private Date passDate;

    private long batchId;

    private String customerName;

    private String customerPhone;

    private String customerAddress;
}
