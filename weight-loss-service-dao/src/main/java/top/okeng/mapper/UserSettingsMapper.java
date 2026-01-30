package top.okeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.okeng.entity.UserSettings;

@Mapper
public interface UserSettingsMapper extends BaseMapper<UserSettings> {
}
