package com.btl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "location")
public class Stored {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String locationType;

    private int locationName; // chứa thông tin nơi lưu kho
    // factory -> 1000, dealer -> 2000, service -> 3000 (kho nào, trung tâm bảo hành nào ...)

    @OneToMany(mappedBy = "location")
    private Set<Products> products;
}
