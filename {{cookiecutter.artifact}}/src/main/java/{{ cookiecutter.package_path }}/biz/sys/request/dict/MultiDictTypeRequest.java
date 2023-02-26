package {{ cookiecutter.basePackage }}.biz.sys.request.dict;

import lombok.Data;

import java.util.List;


@Data
public class MultiDictTypeRequest {

    /**
     * 多DictType
     */
    private List<String> types;
}
