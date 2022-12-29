package com.btl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/* Lưu trữ thông tin giao dịch  */

@Data
@Entity
@Table(name = "batch_manage")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;

    @Temporal(TemporalType.DATE)
    private Date date;
    private long fromId;

    private long toId;

    private long quantity;

    private String status;

    private long price;

    @Temporal(TemporalType.DATE)
    private Date warrantyDate;
}
