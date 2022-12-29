package com.btl.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BillCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String customerName;

    private String customerAddress;

    private String customerPhone;

    private long batchId;

}
