package top.okeng.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Repository;
import top.okeng.entity.UserSettings;
import top.okeng.mapper.UserSettingsMapper;

import java.util.Optional;

@Repository
public class UserSettingsRepository {

    private final UserSettingsMapper userSettingsMapper;

    public UserSettingsRepository(UserSettingsMapper userSettingsMapper) {
        this.userSettingsMapper = userSettingsMapper;
    }

    public int insert(UserSettings settings) {
        return userSettingsMapper.insert(settings);
    }

    public int update(UserSettings settings) {
        return userSettingsMapper.updateById(settings);
    }

    public Optional<UserSettings> findByUserId(Long userId) {
        QueryWrapper<UserSettings> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return Optional.ofNullable(userSettingsMapper.selectOne(queryWrapper));
    }
}
