package {{ cookiecutter.basePackage }}.biz.site.realname;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.des.Sensitive;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实名认证
 */
@Data
@TableName("site_realname")
@EqualsAndHashCode(callSuper = false)
public class Realname extends BaseEntity {

    /**
     * 姓名
     */
    @Sensitive(DesensitizedUtil.DesensitizedType.CHINESE_NAME)
    private String name;

    /**
     * 证件类型
     */
    private IdTypeEnum idType;

    /**
     * 证件号码
     */
    @Sensitive(DesensitizedUtil.DesensitizedType.ID_CARD)
    private String idNum;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 认证状态
     */
    private VerificationStatusEnum status;

}