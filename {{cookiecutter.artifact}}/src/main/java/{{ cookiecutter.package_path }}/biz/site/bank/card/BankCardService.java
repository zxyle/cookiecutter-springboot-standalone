package {{ cookiecutter.basePackage }}.biz.site.bank.card;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 银行卡号 服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "BankCardCache")
public class BankCardService extends ServiceImpl<BankCardMapper, BankCard> {

    /**
     * 新增银行卡号
     */
    @CachePut(key = "#result.id")
    public BankCard insert(BankCard entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按ID查询银行卡号
     */
    @Cacheable(key = "#id")
    public BankCard findById(Integer id) {
        return getById(id);
    }

    /**
     * 查询指定用户的数据
     */
    @Cacheable(key = "#id + '_' + #userId")
    public BankCard findById(Integer id, Integer userId) {
        LambdaQueryWrapper<BankCard> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, BankCard::getId, id);
        wrapper.eq(userId != null, BankCard::getUserId, userId);
        return getOne(wrapper);
    }

    /**
     * 按ID删除银行卡号
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    /**
     * 删除指定用户的数据
     */
    @CacheEvict(key = "#id + '_' + #userId")
    public boolean deleteById(Integer id, Integer userId) {
        LambdaQueryWrapper<BankCard> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, BankCard::getId, id);
        wrapper.eq(userId != null, BankCard::getUserId, userId);
        return remove(wrapper);
    }

}