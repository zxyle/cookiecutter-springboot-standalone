package {{ cookiecutter.basePackage }}.common.response;


public interface TreeNode2 {

    /**
     * 获取节点名称
     *
     * @return 节点名称
     */
    String getName();

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    String getParentId();

    /**
     * 获取节点ID
     *
     * @return 节点ID
     */
    String getId();
}

