package {{ cookiecutter.basePackage }}.biz.sample.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class FileController {

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile() throws IOException {
        String filename = "D:\\apache-tomcat-8.5.59.zip";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/zip")).body(resource);
    }

    /**
     * 单文件上传
     * <p>
     * 注意事项:
     * 1. PostMan中 form-data中的key 需要设置为该方法的形参名, 此例为file
     * 2. 如果不同, 需要使用@RequestParam 进行指定
     */
    @PostMapping(value = "/upload1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String upload1(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败, 请选择文件";
        }

        String fileName = file.getOriginalFilename();
        System.out.println("原始文件名是: " + fileName);

        String contentType = file.getContentType();
        System.out.println(contentType);

        System.out.println(file.getName());

        System.out.println("文件大小: " + file.getSize() + "字节");

        String filePath = "D:/";
        File dest = new File(filePath + fileName);

        try {
            file.transferTo(dest);
            return "上传到: " + dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 处理多个文件上传
     */
    @PostMapping(value = "/upload2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String upload2(MultipartFile[] files) {
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            System.out.println(file.getSize());
            System.out.println(filename);
        }

        return "上传成功";
    }
}
