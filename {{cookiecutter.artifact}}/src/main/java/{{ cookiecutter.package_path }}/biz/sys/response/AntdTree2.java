package {{ cookiecutter.basePackage }}.biz.sys.response;


import java.util.List;

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
     * @mock 330000
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

    public AntdTree2() {
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<? extends AntdTree2> getChildren() {
        return this.children;
    }

    public void setChildren(List<? extends AntdTree2> children) {
        this.children = children;
    }
}
