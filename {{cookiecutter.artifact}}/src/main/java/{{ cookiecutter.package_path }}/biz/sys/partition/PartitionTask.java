package {{ cookiecutter.basePackage }}.biz.sys.partition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 创建和删除分区定时任务
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PartitionTask {

    final PartitionMapper partitionMapper;

    // 需要创建和删除分区的表
    private static final String[] TABLE_NAMES = {
            // "device_breaker_state",  // 智能开关数据表
    };

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 每天23:55 创建明天分区
     */
    @Scheduled(cron = "0 55 23 * * ?")
    public void createPartitions() {
        if (TABLE_NAMES.length == 0) {
            return;
        }

        String tomorrow = LocalDate.now().plusDays(1).format(formatter);
        String theDayAfterTomorrow = LocalDate.now().plusDays(2).format(formatter);
        String partitionName = "p" + tomorrow;
        for (String tableName : TABLE_NAMES) {
            boolean existed = partitionMapper.existsPartition(tableName, partitionName);
            if (existed) {
                log.info("创建日期分区 {}: 日期分区({})已存在", tableName, partitionName);
                continue;
            }

            try {
                partitionMapper.createPartition(tableName, partitionName, theDayAfterTomorrow);
                log.info("创建日期分区 {} 创建日期分区表({})成功", tableName, partitionName);
            } catch (Exception exception) {
                log.error("创建日期分区 {} 创建日期分区表({})失败, 原因: {}", tableName, partitionName, exception.getMessage());
            }
        }
    }

    /**
     * 每日00:10 删除两年前的分区
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void dropPartitions() {
        if (TABLE_NAMES.length == 0) {
            return;
        }

        String twoYearsAgo = LocalDate.now().minusDays(2 * 365).format(formatter);
        String partitionName = "p" + twoYearsAgo;

        for (String tableName : TABLE_NAMES) {
            boolean existed = partitionMapper.existsPartition(tableName, partitionName);
            if (existed) {
                log.info("删除日期分区 {}: 日期分区({})不存在", tableName, partitionName);
                continue;
            }

            try {
                partitionMapper.dropPartition(tableName, partitionName);
                log.info("删除日期分区  {}: 删除日期分区({})成功", tableName, partitionName);
            } catch (Exception exception) {
                log.error("删除日期分区  {}: 删除日期分区({})失败", tableName, partitionName);
            }
        }
    }
}
