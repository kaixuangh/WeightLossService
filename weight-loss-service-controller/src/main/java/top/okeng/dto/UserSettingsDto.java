package top.okeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDto {
    private BigDecimal height;
    private BigDecimal targetWeight;
    private String weightUnit;
    private Boolean reminderEnabled;
    private String reminderTime;
}
