package com.btl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "sold")
public class Sold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private Date soldDate;

    private long productId;

    private long dealerId;//địa chỉ của hàng bán

    private String soldPrice; //bảo hành trong bao ngày

    private int warrantyDate;

    private long quantity;

}
