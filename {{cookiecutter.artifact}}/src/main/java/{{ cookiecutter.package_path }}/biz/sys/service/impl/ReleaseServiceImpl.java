package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Release;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.ReleaseMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IReleaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 发布版本 服务实现类
 */
@Service
public class ReleaseServiceImpl extends ServiceImpl<ReleaseMapper, Release> implements IReleaseService {

}
