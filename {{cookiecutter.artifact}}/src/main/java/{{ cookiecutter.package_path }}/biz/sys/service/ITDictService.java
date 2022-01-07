package {{ cookiecutter.basePackage }}.biz.sys.service;

import {{ cookiecutter.basePackage }}.biz.sys.entity.TDict;
import {{ cookiecutter.basePackage }}.biz.sys.request.DictRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典数据表 服务类
 */
public interface ITDictService extends IService<TDict> {

    IPage<TDict> getPageList(DictRequest request);
}