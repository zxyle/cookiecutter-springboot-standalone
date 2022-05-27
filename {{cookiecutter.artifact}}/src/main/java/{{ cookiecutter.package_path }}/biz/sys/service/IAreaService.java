package {{ cookiecutter.basePackage }}.biz.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Area;

/**
 * 行政区 服务类
 */
public interface IAreaService extends IService<Area> {

    Area getAreaByCode(String code);

}
