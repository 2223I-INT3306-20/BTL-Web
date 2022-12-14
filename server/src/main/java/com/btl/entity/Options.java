package com.btl.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.json.JSONObject;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "option_product")
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private long optionId;
    private String optionName;
    private double screenSize;
    private double battery;
    private String cpuBrand;
    private String cpuName;
    private int ram;
    private int rom;
    private String gpu;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Products> products;

    public String getOptionInfo() {
        return optionName + ": " + screenSize + " inch, " + cpuBrand + " " + cpuName + ", " + ram + " GB RAM, " + rom + " GB ROM, " + gpu;
    }

}
