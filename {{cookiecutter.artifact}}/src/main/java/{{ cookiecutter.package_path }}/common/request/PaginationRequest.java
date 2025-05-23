package {{ cookiecutter.basePackage }}.common.request;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页请求对象，所有分页请求对象都应该继承此类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaginationRequest extends ConditionRequest {

    protected static final int DEFAULT_PAGE_NUM = 1;      // 默认页码
    protected static final int MAX_PAGE_NUM = 1000;       // 最大页码 避免深分页带来性能问题
    protected static final int DEFAULT_PAGE_SIZE = 10;    // 默认分页大小
    protected static final int MAX_PAGE_SIZE = 100;       // 最大分页大小，防止恶意请求

    /**
     * 分页页码
     *
     * @mock 1
     */
    protected Integer pageNum;

    /**
     * 分页大小
     *
     * @mock 10
     */
    protected Integer pageSize;

    /**
     * 是否跳过总数查询，加快查询速度
     *
     * @mock false
     */
    private Boolean skipTotal;


    /**
     * 获取合法页码
     */
    public Integer getPageNum() {
        return (pageNum == null || pageNum < 1 || pageNum > MAX_PAGE_NUM) ?
                DEFAULT_PAGE_NUM : pageNum;
    }

    /**
     * 获取合法分页大小
     */
    public Integer getPageSize() {
        return (pageSize == null || pageSize < 1 || pageSize > MAX_PAGE_SIZE) ?
                DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 计算分页偏移量
     */
    public Integer getOffset() {
        return (getPageNum() - 1) * getPageSize();
    }

    /**
     * 获取mybatis plus翻页对象
     */
    public <T> Page<T> getPage() {
        Page<T> page = new Page<>(getPageNum(), getPageSize(), (skipTotal == null || !skipTotal));

        if (!calledSort) {
            List<OrderItem> orderItems = super.getOrderItems();
            page.addOrder(orderItems);
            super.calledSort = true;
        }
        return page;
    }

}
