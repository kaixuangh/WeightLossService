package top.okeng.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import top.okeng.dto.WeightRecordDto;
import top.okeng.dto.WeightRecordRequest;
import top.okeng.dto.WeightRecordsResponse;
import top.okeng.entity.User;
import top.okeng.entity.WeightRecord;
import top.okeng.rpc.response.RPCBaseResult;
import top.okeng.rpc.template.SOAProviderTemplate;
import top.okeng.services.UserService;
import top.okeng.services.WeightRecordService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/weight")
public class WeightController extends SOAProviderTemplate {

    private final UserService userService;
    private final WeightRecordService weightRecordService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public WeightController(UserService userService, WeightRecordService weightRecordService) {
        this.userService = userService;
        this.weightRecordService = weightRecordService;
    }

    @PostMapping("/record")
    public RPCBaseResult<?> addOrUpdateRecord(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody WeightRecordRequest request) {
        return execute(() -> {
            User user = userService.findByUsername(userDetails.getUsername());
            LocalDate date = LocalDate.parse(request.getDate(), DATE_FORMATTER);
            WeightRecord record = weightRecordService.addOrUpdateRecord(user.getId(), date, request.getWeight());
            return WeightRecordDto.fromEntity(record);
        }, SOAProviderTemplate::getFail);
    }

    @GetMapping("/records")
    public RPCBaseResult<?> getRecords(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate,
                                       @RequestParam(required = false) Integer days) {
        return execute(() -> {
            User user = userService.findByUsername(userDetails.getUsername());

            LocalDate start = startDate != null ? LocalDate.parse(startDate, DATE_FORMATTER) : null;
            LocalDate end = endDate != null ? LocalDate.parse(endDate, DATE_FORMATTER) : null;

            List<WeightRecord> records = weightRecordService.getRecords(user.getId(), start, end, days);
            WeightRecordService.WeightStatistics stats = weightRecordService.calculateStatistics(records);

            List<WeightRecordDto> recordDtos = records.stream()
                    .map(WeightRecordDto::simpleFromEntity)
                    .collect(Collectors.toList());

            WeightRecordsResponse.Statistics statistics = new WeightRecordsResponse.Statistics(
                    stats.getMaxWeight(),
                    stats.getMinWeight(),
                    stats.getAvgWeight(),
                    stats.getLatestWeight(),
                    stats.getChange()
            );

            return new WeightRecordsResponse(recordDtos, statistics);
        }, SOAProviderTemplate::getFail);
    }

    @GetMapping("/record/{date}")
    public RPCBaseResult<?> getRecordByDate(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable String date) {
        return execute(() -> {
            User user = userService.findByUsername(userDetails.getUsername());
            LocalDate recordDate = LocalDate.parse(date, DATE_FORMATTER);
            WeightRecord record = weightRecordService.getRecordByDate(user.getId(), recordDate);
            return WeightRecordDto.fromEntity(record);
        }, SOAProviderTemplate::getFail);
    }

    @DeleteMapping("/record/{id}")
    public RPCBaseResult<?> deleteRecord(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long id) {
        return executeWithoutResult(() -> {
            User user = userService.findByUsername(userDetails.getUsername());
            weightRecordService.deleteRecord(user.getId(), id);
            return null;
        }, SOAProviderTemplate::getFail);
    }
}
