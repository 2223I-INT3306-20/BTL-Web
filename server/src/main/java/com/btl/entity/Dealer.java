package com.btl.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dealer extends Location{

    // Đại lý phân phối sản phẩm

    private long id;                // mã đại lý
    private String name;            // tên đại lý
    private String leader;          // tên người đại diện/quản lý
    private String address;         // địa chỉ
}
