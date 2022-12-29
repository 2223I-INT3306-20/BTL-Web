package com.btl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "fault")
public class Fault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private long productId;

    private long fromId;

    private long serviceId; // địa chỉ nhận hàng lỗi

    private Date receiveDate;

    private Date passDate;

    private String status;


}
