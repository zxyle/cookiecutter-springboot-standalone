package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.entity.FriendlyUrl;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.FriendlyUrlMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IFriendlyUrlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 友链 服务实现类
 */
@Service
public class FriendlyUrlServiceImpl extends ServiceImpl<FriendlyUrlMapper, FriendlyUrl> implements IFriendlyUrlService {

}
