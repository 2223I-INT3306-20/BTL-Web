package com.btl.entity;

public class Product {
    private String id;
    private String batch;
    private String status;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Product(String id, String batch, String status, String price) {
        this.id = id;
        this.batch = batch;
        this.status = status;
        this.price = price;
    }

}
