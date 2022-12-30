package com.btl.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PieChartResponse {
    private List<String> labels = new ArrayList<>();
    private List<Long> datasets;
}
