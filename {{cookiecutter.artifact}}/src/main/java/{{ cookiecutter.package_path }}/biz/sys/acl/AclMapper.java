package {{ cookiecutter.basePackage }}.biz.sys.acl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * IP访问控制 数据访问类
 */
@Repository
public interface AclMapper extends BaseMapper<Acl> {

}