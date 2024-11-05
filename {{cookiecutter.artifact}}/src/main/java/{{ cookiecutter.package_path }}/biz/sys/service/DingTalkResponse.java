package {{ cookiecutter.basePackage }}.biz.sys.service;

import lombok.Data;

/**
 * 钉钉响应
 */
@Data
public class DingTalkResponse {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;
}
