package {{ cookiecutter.basePackage }}.snippet;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class HttpDemo {

    public String post(String url, String json) {
        try (HttpResponse response = HttpRequest.post(url)
                .header("Accept", "application/json")
                .body(json)
                .execute()) {
            if (response.getStatus() != 200) {
                throw new RuntimeException("response status: " + response.getStatus());
            }

            return response.body();
        }
    }

    public String get(String url, Map<String, Object> params) {
        try (HttpResponse response = HttpRequest.get(url)
                .header("Accept", "application/json")
                .form(params)  // 添加请求参数
                .execute()) {

            return response.body();  // 返回响应内容
        }
    }

    public static void main(String[] args) {
        HttpDemo httpDemo = new HttpDemo();
        System.out.println(httpDemo.get("https://httpbin.org/get", ImmutableMap.of("name", "Tom", "age", "10")));
    }
}