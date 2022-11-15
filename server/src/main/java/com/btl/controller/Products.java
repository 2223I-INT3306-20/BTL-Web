package com.btl.controller;


import com.btl.database.ProductData;
import com.btl.entity.Product;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Products {

    @RequestMapping("/products")
    public ResponseEntity<?> getAllProduct() {
        ProductData productData = new ProductData();
        productData.getAllProduct();
        List< Product> products = productData.getProducts();
        return ResponseEntity.ok(products);
    }
}
