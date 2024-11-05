package {{ cookiecutter.basePackage }}.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页响应对象
 */
@Getter
@Setter
@ToString
public class PageVO<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 分页总数
     *
     * @mock 100
     */
    private Long total;

    public PageVO(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

}
