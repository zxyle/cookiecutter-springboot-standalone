package {{ cookiecutter.basePackage }}.common.request;

/**
 * 查询体请求
 */
public class QueryRequest<T> extends PaginationRequest {
    /**
     * 查询体
     */
    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}
