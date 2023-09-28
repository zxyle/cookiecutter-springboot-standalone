package {{ cookiecutter.basePackage }}.biz.sys.acl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * IP访问控制 数据访问类
 */
@Mapper
public interface AclMapper extends BaseMapper<Acl> {

}