package {{ cookiecutter.basePackage }}.biz.sys.request;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;

public class DictRequest extends PaginationRequest {

    /**
     * 字典ID
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
