package com.btl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@NamedQuery(name="Products.findAll", query="SELECT * FROM product")
@Table(name = "product")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    private String productSku;
    @Column(nullable = false, length = 200)
    private String productName;
    private String productPrice;
    private String productWeight;
    private String productImg;
    private int productCategoryId;



    @Temporal(TemporalType.DATE)
    private Date productMfg; // ngày sản xuất
    private long productStock;


    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "option_id")
    @JsonBackReference
    private Options option;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Stored location;

//    @JsonIgnore
//    public String getInfo() {
//        return productSku + ", " + productName + ", " + productPrice + ", " + option.getOptionInfo() + ", " + location.getLocationInfo();
//    }


}
