package com.btl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    private String productSku;
    @Column(nullable = false, length = 200)
    private String productName;

    @Temporal(TemporalType.DATE)
    private Date productMfg; // ngày sản xuất
    private long warranty;
    private long productStock;

    private long daXuat;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "option_id")
    @JsonBackReference
    private Options option;

    @JsonIgnore
    public String getInfo() {
        return productSku + ", " + productName + ", " + option.getOptionInfo();
    }


}
