package {{ cookiecutter.basePackage }}.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AbstractListener<T> implements ReadListener<T> {

    private IService<T> service;

    public void setService(IService<T> service) {
        this.service = service;
    }

    /**
     * 单次缓存的数据量
     */
    public static final int BATCH_COUNT = 100;

    /**
     * 临时存储
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        cachedDataList.add(t);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        boolean b = service.saveBatch(cachedDataList);
        if (!b) {
            log.info("数据批量插入失败！");
        } else {
            log.info("存储数据库成功！");
        }
    }
}
