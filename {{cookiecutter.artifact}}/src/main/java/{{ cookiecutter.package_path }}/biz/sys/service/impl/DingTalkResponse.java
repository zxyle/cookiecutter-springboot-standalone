package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import lombok.Data;

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
