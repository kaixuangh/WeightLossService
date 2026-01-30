package top.okeng.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Repository;
import top.okeng.entity.WeightRecord;
import top.okeng.mapper.WeightRecordMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class WeightRecordRepository {

    private final WeightRecordMapper weightRecordMapper;

    public WeightRecordRepository(WeightRecordMapper weightRecordMapper) {
        this.weightRecordMapper = weightRecordMapper;
    }

    public int insert(WeightRecord record) {
        return weightRecordMapper.insert(record);
    }

    public int update(WeightRecord record) {
        return weightRecordMapper.updateById(record);
    }

    public int deleteById(Long id) {
        return weightRecordMapper.deleteById(id);
    }

    public Optional<WeightRecord> findById(Long id) {
        return Optional.ofNullable(weightRecordMapper.selectById(id));
    }

    public Optional<WeightRecord> findByUserIdAndDate(Long userId, LocalDate date) {
        QueryWrapper<WeightRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("record_date", date);
        return Optional.ofNullable(weightRecordMapper.selectOne(queryWrapper));
    }

    public List<WeightRecord> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        QueryWrapper<WeightRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .ge("record_date", startDate)
                .le("record_date", endDate)
                .orderByDesc("record_date");
        return weightRecordMapper.selectList(queryWrapper);
    }

    public List<WeightRecord> findByUserIdLastDays(Long userId, int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);
        return findByUserIdAndDateRange(userId, startDate, endDate);
    }
}
