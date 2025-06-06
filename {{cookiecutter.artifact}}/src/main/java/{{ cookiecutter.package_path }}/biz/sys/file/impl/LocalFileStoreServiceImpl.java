package {{ cookiecutter.basePackage }}.biz.sys.file.impl;

import {{ cookiecutter.basePackage }}.biz.sys.file.FileStoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 本地文件存储实现（这种实现方式并不安全，仅用于测试）
 */
@Service
public class LocalFileStoreServiceImpl implements FileStoreService {

    private static final String LOCAL_FILE_STORE = "/tmp";

    /**
     * 上传文件
     *
     * @param file       文件
     * @param objectName 文件名
     * @return 文件地址
     */
    @Override
    public String upload(MultipartFile file, String objectName) {
        // 将文件保存到本地
        File target = new File(LOCAL_FILE_STORE, objectName);
        File parentFile = target.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try (FileOutputStream fs = new FileOutputStream(target)) {
            fs.write(file.getBytes());
            return target.getPath();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * 删除文件
     *
     * @param objectName 文件名
     */
    @Override
    public boolean delete(String objectName) {
        File file = new File(LOCAL_FILE_STORE, objectName);
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断文件是否存在
     *
     * @param objectName 文件名
     * @return 存在则返回url, 不存在则返回null
     */
    @Override
    public String exists(String objectName) {
        return null;
    }
}
