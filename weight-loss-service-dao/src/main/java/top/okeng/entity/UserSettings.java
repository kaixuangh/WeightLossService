package top.okeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_settings")
public class UserSettings {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private BigDecimal height;
    private BigDecimal targetWeight;
    private String weightUnit = "KG";
    private Boolean reminderEnabled = false;
    private String reminderTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
