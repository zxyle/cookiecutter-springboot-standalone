package {{ cookiecutter.basePackage }}.biz.sys.request;

import {{ cookiecutter.basePackage }}.base.request.PageRequest;

public class DictRequest extends PageRequest {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
