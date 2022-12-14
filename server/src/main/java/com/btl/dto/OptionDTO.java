package com.btl.dto;

import lombok.Data;

@Data
public class OptionDTO {

    private String optionName;
    private double screenSize;
    private double battery;
    private String cpuBrand;
    private String cpuName;
    private int ram;
    private int rom;
    private String gpu;
}
