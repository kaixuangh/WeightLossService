package top.okeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightRecordsResponse {
    private List<WeightRecordDto> records;
    private Statistics statistics;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Statistics {
        private BigDecimal maxWeight;
        private BigDecimal minWeight;
        private BigDecimal avgWeight;
        private BigDecimal latestWeight;
        private BigDecimal change;
    }
}
