package com.btl.entity;

import lombok.Data;

@Data
public abstract class Location {

    private long locationId;
    private String locationName;
    private String locationAddress;
    private String loactionLeader;

}
