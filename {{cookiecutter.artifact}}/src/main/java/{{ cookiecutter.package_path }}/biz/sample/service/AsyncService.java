package {{ cookiecutter.basePackage }}.biz.sample.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async  // 告诉spring 这是一个异步的方法
    public void process() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("处理数据中...");
    }
}