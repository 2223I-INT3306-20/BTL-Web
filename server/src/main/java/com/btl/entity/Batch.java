package com.btl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


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
}
