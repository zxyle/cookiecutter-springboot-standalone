package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Blacklist;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.IpBlacklistMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IIpBlacklistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * IP黑名单 服务实现类
 */
@Service
public class IpBlacklistServiceImpl extends ServiceImpl<IpBlacklistMapper, Blacklist> implements IIpBlacklistService {

}
