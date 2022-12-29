package com.btl.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComponentResponse {
    private String label;
    private List<Long> data = new ArrayList<>();
}
