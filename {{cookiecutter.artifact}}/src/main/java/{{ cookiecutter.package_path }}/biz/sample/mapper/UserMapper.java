package {{ cookiecutter.basePackage }}.biz.sample.mapper;

import {{ cookiecutter.basePackage }}.biz.sample.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}