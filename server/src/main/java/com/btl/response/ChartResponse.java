package com.btl.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChartResponse {

    private List<String> labels = new ArrayList<>();
    private List<ComponentResponse> datasets = new ArrayList<ComponentResponse>();
}
