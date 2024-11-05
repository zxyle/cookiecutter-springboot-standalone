package {{ cookiecutter.basePackage }}.biz.sys.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储服务
 */
public interface FileStoreService {

    /**
     * 上传文件
     *
     * @param file       文件
     * @param objectName 文件名
     * @return 文件地址
     */
    String upload(MultipartFile file, String objectName);

    /**
     * 删除文件
     *
     * @param objectName 文件名
     * @return 是否删除成功
     */
    boolean delete(String objectName);

    /**
     * 判断文件是否存在
     *
     * @param objectName 文件名
     * @return 存在则返回url, 不存在则返回null
     */
    String exists(String objectName);
}
