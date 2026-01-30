package top.okeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightRecordDto {
    private String id;
    private String date;
    private BigDecimal weight;
    private String createdAt;
    private String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    public static WeightRecordDto fromEntity(top.okeng.entity.WeightRecord record) {
        WeightRecordDto dto = new WeightRecordDto();
        dto.setId(String.valueOf(record.getId()));
        dto.setDate(record.getRecordDate().toString());
        dto.setWeight(record.getWeight());
        if (record.getCreatedAt() != null) {
            dto.setCreatedAt(record.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).format(FORMATTER));
        }
        if (record.getUpdatedAt() != null) {
            dto.setUpdatedAt(record.getUpdatedAt().atZone(java.time.ZoneId.systemDefault()).format(FORMATTER));
        }
        return dto;
    }

    public static WeightRecordDto simpleFromEntity(top.okeng.entity.WeightRecord record) {
        WeightRecordDto dto = new WeightRecordDto();
        dto.setId(String.valueOf(record.getId()));
        dto.setDate(record.getRecordDate().toString());
        dto.setWeight(record.getWeight());
        return dto;
    }
}
