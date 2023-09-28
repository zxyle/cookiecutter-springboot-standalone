package {{ cookiecutter.basePackage }}.biz.sys.partition;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分区表操作 Mapper 接口
 */
@Mapper
public interface PartitionMapper {

    /**
     * 创建日期分区
     *
     * @param tableName     待创建分区的表名
     * @param partitionName 待创建的分区名 例如：p20210101
     * @param tomorrow      明天的日期 例如：20210102
     */
    void createPartition(String tableName, String partitionName, String tomorrow);

    /**
     * 删除日期分区
     *
     * @param tableName     待删除分区的表名
     * @param partitionName 待删除的分区名 例如：p20210101
     */
    void dropPartition(String tableName, String partitionName);

    /**
     * 检查分区是否存在
     *
     * @param tableName     待检查分区的表名
     * @param partitionName 待检查的分区名 例如：p20210101
     * @return 0 不存在 1 存在
     */
    Integer checkPartition(String tableName, String partitionName);

    /**
     * 获取表的所有分区名
     *
     * @param tableName 待获取分区的表名
     * @return 分区名列表
     */
    List<String> listPartitions(String tableName);
}