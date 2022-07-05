package {{ cookiecutter.basePackage }}.biz.auth.service.impl;

import {{ cookiecutter.basePackage }}.biz.auth.service.ShortMessageService;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务
 * <a href="https://help.aliyun.com/document_detail/419273.htm">阿里云短信服务文档</a>
 */
@Service
public class AliyunShortMessageServiceImpl implements ShortMessageService {

    @Override
    public boolean send(String mobile, String code) {
        String url;
        return false;
    }
}
