package top.okeng.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import top.okeng.dto.UserSettingsDto;
import top.okeng.entity.User;
import top.okeng.entity.UserSettings;
import top.okeng.rpc.response.RPCBaseResult;
import top.okeng.rpc.template.SOAProviderTemplate;
import top.okeng.services.UserService;
import top.okeng.services.UserSettingsService;

@RestController
@RequestMapping("/api/user")
public class UserController extends SOAProviderTemplate {

    private final UserService userService;
    private final UserSettingsService userSettingsService;

    public UserController(UserService userService, UserSettingsService userSettingsService) {
        this.userService = userService;
        this.userSettingsService = userSettingsService;
    }

    @GetMapping("/settings")
    public RPCBaseResult<?> getSettings(@AuthenticationPrincipal UserDetails userDetails) {
        return execute(() -> {
            User user = userService.findByUsername(userDetails.getUsername());
            UserSettings settings = userSettingsService.getSettings(user.getId());

            UserSettingsDto dto = new UserSettingsDto();
            dto.setHeight(settings.getHeight());
            dto.setTargetWeight(settings.getTargetWeight());
            dto.setWeightUnit(settings.getWeightUnit());
            dto.setReminderEnabled(settings.getReminderEnabled());
            dto.setReminderTime(settings.getReminderTime());
            return dto;
        }, SOAProviderTemplate::getFail);
    }

    @PutMapping("/settings")
    public RPCBaseResult<?> updateSettings(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody UserSettingsDto settingsDto) {
        return executeWithoutResult(() -> {
            User user = userService.findByUsername(userDetails.getUsername());
            userSettingsService.updateSettings(
                    user.getId(),
                    settingsDto.getHeight(),
                    settingsDto.getTargetWeight(),
                    settingsDto.getWeightUnit(),
                    settingsDto.getReminderEnabled(),
                    settingsDto.getReminderTime()
            );
            return null;
        }, SOAProviderTemplate::getFail);
    }
}
