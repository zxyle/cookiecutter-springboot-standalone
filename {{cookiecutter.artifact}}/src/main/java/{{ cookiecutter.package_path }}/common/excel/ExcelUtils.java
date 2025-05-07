package {{ cookiecutter.basePackage }}.common.excel;

import com.alibaba.excel.EasyExcel;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel导出工具类
 * <p>
 * 该类提供了通用的Excel导出方法，使用EasyExcel库进行Excel文件的生成和下载。
 * </p>
 */
public final class ExcelUtils {

    private ExcelUtils() {
        // 私有构造器防止实例化
    }

    /**
     * 通用Excel导出方法
     *
     * @param response  HttpServletResponse对象
     * @param fileName  导出文件名（不含扩展名）
     * @param dataList  要导出的数据集合
     * @param template  数据映射类
     * @param sheetName 工作表名称
     * @param <T>       数据类型
     * @throws IOException 抛出IO异常
     */
    public static <T> void export(HttpServletResponse response,
                                       String fileName,
                                       List<T> dataList,
                                       Class<T> template,
                                       String sheetName) throws IOException {
        // 设置响应类型和编码
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        // 处理文件名编码
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8")
                .replaceAll("\\+", "%20");
        String contentDisposition = String.format("attachment;filename*=utf-8''%s.xlsx", encodedFileName);
        response.setHeader("Content-Disposition", contentDisposition);

        // 使用EasyExcel写入数据
        EasyExcel.write(response.getOutputStream(), template)
                .autoCloseStream(false)  // 防止关闭响应流
                .sheet(sheetName)
                .doWrite(dataList);
    }

    /**
     * 重载方法，默认使用Sheet1作为工作表名称
     */
    public static <T> void export(HttpServletResponse response,
                                       String fileName,
                                       List<T> dataList,
                                       Class<T> template) throws IOException {
        export(response, fileName, dataList, template, "Sheet1");
    }
}