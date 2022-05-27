package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Info;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.InfoMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统信息 服务实现类
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {

}
