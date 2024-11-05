package {{ cookiecutter.basePackage }}.biz.sys.file;

import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Blob;

/**
 * 文件
 */
@Data
@TableName("sys_file")
@EqualsAndHashCode(callSuper = false)
public class File extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件内容
     */
    private Blob content;

    /**
     * 文件下载URL
     */
    private String url;

}
