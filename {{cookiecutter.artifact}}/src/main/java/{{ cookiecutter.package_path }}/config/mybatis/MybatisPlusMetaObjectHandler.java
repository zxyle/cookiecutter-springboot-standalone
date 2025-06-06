package {{ cookiecutter.basePackage }}.config.mybatis;

import cn.hutool.core.util.StrUtil;
import {{ cookiecutter.basePackage }}.common.constant.ProjectConst;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import {{ cookiecutter.basePackage }}.config.security.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MybatisPlus自动填充
 * 用于自动填充创建时间、更新时间、创建人(如果字段存在)、更新人(如果字段存在)
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_FIELD = StrUtil.toCamelCase(ProjectConst.CREATE_FIELD);
    private static final String CREATOR_FIELD = StrUtil.toCamelCase(ProjectConst.CREATOR_FIELD);
    private static final String UPDATE_FIELD = StrUtil.toCamelCase(ProjectConst.UPDATE_FIELD);
    private static final String UPDATER_FIELD = StrUtil.toCamelCase(ProjectConst.UPDATER_FIELD);

    /**
     * mybatis plus 插入时填充策略
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(CREATE_FIELD, LocalDateTime.now(), metaObject);
        this.setFieldValByName(UPDATE_FIELD, LocalDateTime.now(), metaObject);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 新增时填充创建人、更新人
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            this.setFieldValByName(CREATOR_FIELD, loginUser.getUser().getId(), metaObject);
            this.setFieldValByName(UPDATER_FIELD, loginUser.getUser().getId(), metaObject);
        }
        // 逻辑删除
        // this.setFieldValByName(ProjectConst.LOGIC_DELETE_FIELD, ProjectConst.LOGIC_NOT_DELETED, metaObject);
    }

    /**
     * mybatis plus 更新时填充策略
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(UPDATE_FIELD, LocalDateTime.now(), metaObject);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 更新时填充更新人
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            this.setFieldValByName(UPDATER_FIELD, loginUser.getUser().getId(), metaObject);
        }
    }
}
