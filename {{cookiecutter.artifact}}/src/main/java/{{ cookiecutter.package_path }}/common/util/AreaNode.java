package {{ cookiecutter.basePackage }}.common.util;

import lombok.Data;

@Data
public class AreaNode implements TreeNode {
    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点父ID
     */
    private String parentId;

    /**
     * 节点ID
     */
    private String id;

    public AreaNode(String name, String parentId, String id) {
        this.name = name;
        this.parentId = parentId;
        this.id = id;
    }

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
