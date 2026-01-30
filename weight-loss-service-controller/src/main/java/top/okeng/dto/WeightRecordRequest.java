package top.okeng.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeightRecordRequest {
    private String date;
    private BigDecimal weight;
}
