package com.btl.entity;

import lombok.Data;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data

public class ProductOptions {

    private int productOptionId;

    private int optionId;

    private int productId;

    private double priceIncrement;


    private Products products;
}
