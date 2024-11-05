package {{ cookiecutter.basePackage }}.biz.site.checkin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 签到 服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "CheckinCache")
public class CheckinService extends ServiceImpl<CheckinMapper, Checkin> {

    final CheckinMapper thisMapper;

    /**
     * 新增签到（带缓存）
     */
    @CachePut(key = "#result.id", unless = "#result == null")
    public Checkin insert(Checkin entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public Checkin findById(Integer id) {
        return getById(id);
    }

    public boolean hasCheckin(Integer userId, LocalDate date) {
        LambdaQueryWrapper<Checkin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Checkin::getUserId, userId);
        wrapper.eq(Checkin::getCheckinDate, date);
        return exists(wrapper);
    }

    public void checkin(Integer userId, LocalDate date) {
        Checkin checkin = new Checkin();
        checkin.setUserId(userId);
        checkin.setCheckinDate(date);
        insert(checkin);
    }

    // 连续签到天数计算

}