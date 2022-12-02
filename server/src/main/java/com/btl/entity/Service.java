package com.btl.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service extends Location{

    //Trung tâm bảo hành sản phẩm

    private long id;                // mã trung tâm bảo hành
    private String name;            // tên trung tâm bảo hành
    private String leader;          // tên người đại diện/quản lý
    private String address;         // địa chỉ

}
