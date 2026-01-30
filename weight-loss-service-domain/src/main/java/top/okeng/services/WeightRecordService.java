package top.okeng.services;

import org.springframework.stereotype.Service;
import top.okeng.entity.WeightRecord;
import top.okeng.exception.BaseError;
import top.okeng.repository.WeightRecordRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;

    public WeightRecordService(WeightRecordRepository weightRecordRepository) {
        this.weightRecordRepository = weightRecordRepository;
    }

    public WeightRecord addOrUpdateRecord(Long userId, LocalDate date, BigDecimal weight) {
        WeightRecord record = weightRecordRepository.findByUserIdAndDate(userId, date)
                .orElse(null);

        if (record != null) {
            record.setWeight(weight);
            weightRecordRepository.update(record);
        } else {
            record = new WeightRecord();
            record.setUserId(userId);
            record.setRecordDate(date);
            record.setWeight(weight);
            weightRecordRepository.insert(record);
        }

        return weightRecordRepository.findByUserIdAndDate(userId, date).orElse(record);
    }

    public WeightRecord getRecordByDate(Long userId, LocalDate date) {
        return weightRecordRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> BaseError.RECORD_NOT_FOUND.getException());
    }

    public WeightRecord getRecordById(Long userId, Long id) {
        WeightRecord record = weightRecordRepository.findById(id)
                .orElseThrow(() -> BaseError.RECORD_NOT_FOUND.getException());

        if (!record.getUserId().equals(userId)) {
            throw BaseError.RECORD_NOT_FOUND.getException();
        }

        return record;
    }

    public void deleteRecord(Long userId, Long id) {
        WeightRecord record = getRecordById(userId, id);
        weightRecordRepository.deleteById(record.getId());
    }

    public List<WeightRecord> getRecords(Long userId, LocalDate startDate, LocalDate endDate, Integer days) {
        if (startDate != null && endDate != null) {
            return weightRecordRepository.findByUserIdAndDateRange(userId, startDate, endDate);
        }

        int queryDays = (days != null && days > 0) ? days : 30;
        return weightRecordRepository.findByUserIdLastDays(userId, queryDays);
    }

    public WeightStatistics calculateStatistics(List<WeightRecord> records) {
        if (records == null || records.isEmpty()) {
            return new WeightStatistics();
        }

        BigDecimal maxWeight = records.stream()
                .map(WeightRecord::getWeight)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal minWeight = records.stream()
                .map(WeightRecord::getWeight)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal avgWeight = records.stream()
                .map(WeightRecord::getWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(records.size()), 2, RoundingMode.HALF_UP);

        BigDecimal latestWeight = records.get(0).getWeight();

        BigDecimal change = BigDecimal.ZERO;
        if (records.size() > 1) {
            BigDecimal oldestWeight = records.get(records.size() - 1).getWeight();
            change = latestWeight.subtract(oldestWeight);
        }

        return new WeightStatistics(maxWeight, minWeight, avgWeight, latestWeight, change);
    }

    public static class WeightStatistics {
        private BigDecimal maxWeight;
        private BigDecimal minWeight;
        private BigDecimal avgWeight;
        private BigDecimal latestWeight;
        private BigDecimal change;

        public WeightStatistics() {
        }

        public WeightStatistics(BigDecimal maxWeight, BigDecimal minWeight, BigDecimal avgWeight,
                                BigDecimal latestWeight, BigDecimal change) {
            this.maxWeight = maxWeight;
            this.minWeight = minWeight;
            this.avgWeight = avgWeight;
            this.latestWeight = latestWeight;
            this.change = change;
        }

        public BigDecimal getMaxWeight() {
            return maxWeight;
        }

        public BigDecimal getMinWeight() {
            return minWeight;
        }

        public BigDecimal getAvgWeight() {
            return avgWeight;
        }

        public BigDecimal getLatestWeight() {
            return latestWeight;
        }

        public BigDecimal getChange() {
            return change;
        }
    }
}
