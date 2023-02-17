package {{ cookiecutter.basePackage }}.biz.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 意见反馈 服务类
 */
public interface IFeedbackService extends IService<Feedback> {

    /**
     * 按ID查询
     */
    Feedback queryById(Long id);

    /**
     * 分页查询
     */
    IPage<Feedback> pageQuery(IPage<Feedback> page);

}
