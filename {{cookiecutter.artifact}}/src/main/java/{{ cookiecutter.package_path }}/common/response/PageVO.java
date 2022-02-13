package {{ cookiecutter.basePackage }}.common.response;

import lombok.ToString;

import java.util.List;

/**
 * 分页响应对象
 */
@ToString
public class PageVO<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 分页总数
     *
     * @mock 10
     */
    private Long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public PageVO(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

}
