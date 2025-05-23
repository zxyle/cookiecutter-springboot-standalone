package {{ cookiecutter.basePackage }}.biz.sys.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import {{ cookiecutter.namespace }}.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 文件上传请求
 */
@Data
public class UploadRequest {

    /**
     * 上传文件目录，默认为根目录下
     */
    private String folder;

    /**
     * 文件列表
     */
    @NotEmpty(message = "文件列表不能为空")
    private List<MultipartFile> files;
}
