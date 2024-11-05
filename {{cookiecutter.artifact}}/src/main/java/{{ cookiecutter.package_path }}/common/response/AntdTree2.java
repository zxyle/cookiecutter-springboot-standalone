package {{ cookiecutter.basePackage }}.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AntdTree2 {

    /**
     * 行政区名称
     *
     * @mock 浙江省
     */
    private String label;

    /**
     * 行政区代码
     *
     * @mock 33
     */
    private String value;

    /**
     * 下一级行政区
     */
    private List<? extends AntdTree2> children;

    public AntdTree2(String label, String value) {
        this.label = label;
        this.value = value;
    }

}
