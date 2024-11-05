package {{ cookiecutter.basePackage }}.biz.sys.file;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 文件 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "FileCache")
public class FileService extends ServiceImpl<FileMapper, File> {

    /**
     * 按ID查询
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public File findById(Integer id) {
        return getById(id);
    }

}
