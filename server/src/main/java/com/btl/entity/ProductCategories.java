package com.btl.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "productCategories")
public class ProductCategories {

    private int categoryId;

    private String categoryName;
}
