package com.btl.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transfer_request")
public class RequestTransfer {
    //Lưu trữ các yêu cầu chuyển hàng của các đại lý với nhà máy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestId;

    private long productId;

    private long quantity;

    private long dealerId;

    private long factoryId;

    private long price;

    private int status; // 1: yêu cầu, 0: đã từ chối, 2: đã chấp nhận


}
