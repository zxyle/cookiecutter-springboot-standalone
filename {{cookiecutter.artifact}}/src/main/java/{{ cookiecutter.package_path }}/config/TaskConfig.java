package {{ cookiecutter.basePackage }}.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync        // 开启异步注解功能
@EnableScheduling   // 开启定时任务功能
public class TaskConfig {
}
