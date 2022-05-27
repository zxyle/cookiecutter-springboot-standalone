package {{ cookiecutter.basePackage }}.common.util;

import lombok.Data;

@Data
public class AreaNode implements TreeNode {
    private String name;
    private String parentId;
    private String id;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public String getId() {
        return id;
    }
}
