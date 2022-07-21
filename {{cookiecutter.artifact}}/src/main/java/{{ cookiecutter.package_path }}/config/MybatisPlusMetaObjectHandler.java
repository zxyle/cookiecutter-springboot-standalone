package {{ cookiecutter.basePackage }}.config;

import cn.hutool.core.util.StrUtil;
import {{ cookiecutter.basePackage }}.common.constant.ProjectConst;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    public static final String CREATE_FIELD = StrUtil.toCamelCase(ProjectConst.CREATE_FIELD);
    public static final String UPDATE_FIELD = StrUtil.toCamelCase(ProjectConst.UPDATE_FIELD);

    // mybatis plus 插入时填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(CREATE_FIELD, LocalDateTime.now(), metaObject);
        this.setFieldValByName(UPDATE_FIELD, LocalDateTime.now(), metaObject);
    }

    // mybatis plus 更新时填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(UPDATE_FIELD, LocalDateTime.now(), metaObject);
    }
}
