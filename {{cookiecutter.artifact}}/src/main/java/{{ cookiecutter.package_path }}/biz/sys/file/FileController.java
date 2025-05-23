package {{ cookiecutter.basePackage }}.biz.sys.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/file")
public class FileController {

    final FileService fileService;
    final StringRedisTemplate stringRedisTemplate;
    final FileStoreService fileStoreService;

    /**
     * 文件上传
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<List<String>> upload(@Valid UploadRequest req) {
        List<String> list = new ArrayList<>(req.getFiles().size());
        List<MultipartFile> files = req.getFiles();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String url = store(file, req.getFolder());
            list.add(url);
        }

        return R.ok(list);
    }

    /**
     * 文件下载
     *
     * @param filename 文件名 竞业协议模板.docx
     */
    // @Secured(value = "ROLE_admin")
    @GetMapping("/download")
    public R<File> download(String filename) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getFilename, filename);
        File one = fileService.getOne(wrapper);
        String key = String.format("%s:%s", "download", one.getId());
        stringRedisTemplate.opsForValue().increment(key);
        return R.ok(one);
    }

    /**
     * 删除已上传文件
     *
     * @param objectName 对象名称 /images/cat.jpg
     */
    @Secured(value = "ROLE_admin")
    @DeleteMapping("/delete")
    public R<Void> delete(@NotBlank String objectName) {
        boolean success = fileStoreService.delete(objectName);
        return R.result(success);
    }

    /**
     * 将文件上传到OSS的bucket中，防止文件重复，在文件名前加时间戳
     *
     * @param file   待上传文件
     * @param folder 上传到指定目录
     */
    private String store(MultipartFile file, String folder) {
        try {
            // 计算文件md5，避免重复上传
            String md5Hex = DigestUtils.md5Hex(file.getBytes());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String objectName = md5Hex + "." + extension;
            if (StringUtils.isNotBlank(folder)) {
                objectName = folder + "/" + objectName;
            }

            // 先判断文件是否存在，如果存在则直接返回url
            String url = fileStoreService.exists(objectName);
            if (StringUtils.isNotBlank(url)) {
                return url;
            }

            return fileStoreService.upload(file, objectName);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return "";
        }
    }
}
