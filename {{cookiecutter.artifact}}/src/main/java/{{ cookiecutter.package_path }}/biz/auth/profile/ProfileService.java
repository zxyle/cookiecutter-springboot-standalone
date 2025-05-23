package {{ cookiecutter.basePackage }}.biz.auth.profile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用户资料 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "ProfileCache")
public class ProfileService extends ServiceImpl<ProfileMapper, Profile> {

    /**
     * 按ID查询资料
     *
     * @param userId 用户ID
     */
    @Cacheable(key = "#userId")
    public Profile findByUserId(Integer userId) {
        LambdaQueryWrapper<Profile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Profile::getUserId, userId);
        return getOne(wrapper);
    }

    /**
     * 更新用户资料
     *
     * @param profile 用户资料
     */
    @CachePut(key = "#profile.userId")
    public Profile updateProfile(Profile profile) {
        LambdaUpdateWrapper<Profile> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Profile::getUserId, profile.getUserId());
        // 如果没有数据，则新增
        if (!exists(wrapper)) {
            save(profile);
            return profile;
        }

        boolean updated = update(profile, wrapper);
        if (updated) {
            // 更新后获取更新后的数据
            return getOne(wrapper);
        }
        return profile;
    }

    /**
     * 删除用户资料
     *
     * @param userId 用户ID
     */
    @CacheEvict(key = "#userId")
    public boolean delete(Integer userId) {
        LambdaQueryWrapper<Profile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Profile::getUserId, userId);
        if (!exists(wrapper)) {
            return true;
        }

        return remove(wrapper);
    }
}
