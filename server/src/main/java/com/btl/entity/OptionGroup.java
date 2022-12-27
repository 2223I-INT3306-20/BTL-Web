package com.btl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
//@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "option_group")
public class OptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_id")
    private int optionGroupId;
    private String optionGroupName;
    private long sold;

//    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    @JsonIgnore
//    private Set<Options> options = new HashSet<>();


}
