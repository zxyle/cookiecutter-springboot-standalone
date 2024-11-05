package {{ cookiecutter.basePackage }}.biz.sys.counter;

import java.util.List;

/**
 * 计数器服务类
 */
public interface CounterService {

    /**
     * 自增并获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     * @return 自增后的统计次数
     */
    Long incr(String biz, String id);

    /**
     * 自增并获取统计次数
     *
     * @param biz  业务名
     * @param id   ID
     * @param step 自增步长
     * @return 自增后的统计次数
     */
    Long incr(String biz, String id, Integer step);

    /**
     * 自减并获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     * @return 自减后的统计次数
     */
    Long decr(String biz, String id);

    /**
     * 获取统计次数
     *
     * @param biz 业务名
     * @param id  ID
     * @return 当前统计次数
     */
    Long get(String biz, String id);

    /**
     * 使用mget批量获取统计次数
     *
     * @param biz 业务名
     * @param ids ID列表
     * @return 统计次数列表
     */
    List<Long> batchGet(String biz, List<Integer> ids);

    /**
     * 清除统计次数
     *
     * @param biz 业务名
     * @param id  ID
     * @return 是否清除成功
     */
    boolean clear(String biz, String id);

    /**
     * 批量清除统计次数
     *
     * @param biz 业务名
     * @param ids ID列表
     * @return 批量清除结果
     */
    boolean batchClear(String biz, List<Integer> ids);
}
