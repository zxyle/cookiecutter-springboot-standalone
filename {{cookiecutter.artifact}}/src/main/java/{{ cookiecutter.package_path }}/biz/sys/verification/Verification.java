package {{ cookiecutter.basePackage }}.biz.sys.verification;

import {{ cookiecutter.basePackage }}.common.entity.RecordEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证码发送记录
 */
@Data
@TableName("sys_verification")
@EqualsAndHashCode(callSuper = false)
public class Verification extends RecordEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 验证码类型
     */
    private String kind;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 内容
     */
    private String content;

}
