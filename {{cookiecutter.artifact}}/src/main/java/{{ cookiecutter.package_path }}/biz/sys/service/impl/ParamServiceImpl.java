package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Param;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.ParamMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统信息 服务实现类
 */
@Service
public class ParamServiceImpl extends ServiceImpl<ParamMapper, Param> implements IParamService {

}
