package {{ cookiecutter.basePackage }}.snippet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Demo {

    public static void main(String[] args) throws IOException {
        // 文件下载
        String url = "https://pay.weixin.qq.com/wiki/doc/api/img/chapter9_1_0.jpg";
        FileUtils.writeByteArrayToFile(new File("/tmp/chapter9_1_0.jpg"), IOUtils.toByteArray(new URL(url)));
    }

}
