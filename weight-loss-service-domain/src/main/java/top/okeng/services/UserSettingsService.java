package top.okeng.services;

import org.springframework.stereotype.Service;
import top.okeng.entity.UserSettings;
import top.okeng.repository.UserSettingsRepository;

import java.math.BigDecimal;

@Service
public class UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;

    public UserSettingsService(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public UserSettings getSettings(Long userId) {
        return userSettingsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserSettings settings = new UserSettings();
                    settings.setUserId(userId);
                    userSettingsRepository.insert(settings);
                    return settings;
                });
    }

    public void updateSettings(Long userId, BigDecimal height, BigDecimal targetWeight,
                               String weightUnit, Boolean reminderEnabled, String reminderTime) {
        UserSettings settings = getSettings(userId);

        if (height != null) {
            settings.setHeight(height);
        }
        if (targetWeight != null) {
            settings.setTargetWeight(targetWeight);
        }
        if (weightUnit != null) {
            settings.setWeightUnit(weightUnit);
        }
        if (reminderEnabled != null) {
            settings.setReminderEnabled(reminderEnabled);
        }
        if (reminderTime != null) {
            settings.setReminderTime(reminderTime);
        }

        userSettingsRepository.update(settings);
    }
}
