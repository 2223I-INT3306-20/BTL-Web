package com.btl.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Factory extends Location{

    //Cơ sở sản xuất

    private long id;                // mã nhà máy
    private String name;            // tên nhà máy
    private String leader;          // tên người đại diện/quản lý
    private String address;         // địa chỉ
}
