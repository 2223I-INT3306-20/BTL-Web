package com.btl.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
//@Entity
@Table(name = "locations")
public class Location {

    private long locationId;
    private String locationName;
    private String locationAddress;
    private String loactionLeader;

}
