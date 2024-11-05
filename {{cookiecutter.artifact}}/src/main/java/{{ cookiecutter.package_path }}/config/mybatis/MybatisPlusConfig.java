package {{ cookiecutter.basePackage }}.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlus配置类
 */
@Configuration
@EnableTransactionManagement   // 开启事务
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 分页插件
        interceptor.addInnerInterceptor(pageInterceptor());
        return interceptor;
    }

    // 分页插件
    public PaginationInnerInterceptor pageInterceptor() {
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        // 单页分页条数限制500条
        pageInterceptor.setMaxLimit(500L);
        pageInterceptor.setDbType(DbType.MYSQL);
        return pageInterceptor;
    }

}
