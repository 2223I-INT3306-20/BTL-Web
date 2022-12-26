package com.btl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
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

    private String name;

    private String locationName; // chứa thông tin nơi lưu kho
    // factory -> 1000, dealer -> 2000, service -> 3000 (kho nào, trung tâm bảo hành nào ...)

    private String address;

    private String phone;



    @ElementCollection
    private Set<Long> productId;

    public String getLocationInfo() {
        return locationType + ", " + locationName;
    }
}
