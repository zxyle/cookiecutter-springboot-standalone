package {{ cookiecutter.basePackage }}.base.request;

public class QueryRequest<T> extends PageRequest {
    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}
