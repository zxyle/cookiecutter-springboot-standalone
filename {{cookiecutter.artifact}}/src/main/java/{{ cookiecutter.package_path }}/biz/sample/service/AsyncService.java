package {{ cookiecutter.basePackage }}.biz.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncService {

    @Async  // 告诉spring 这是一个异步的方法
    public void process() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("error: ", e);
        }
        log.info("处理数据中...");
    }
}