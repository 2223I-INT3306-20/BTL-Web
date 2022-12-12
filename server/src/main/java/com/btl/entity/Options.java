package com.btl.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "options")
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int optionId;

    private int optionGroupId;

    private double screenSize;

    private double battery;

    private String CPUbrand;

    private String CPUname;

    private int RAM;

    private int ROM;

    private String GPU;


}
