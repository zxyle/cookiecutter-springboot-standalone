package {{ cookiecutter.basePackage }}.common.util;

/**
 * 用于构建树的类 需要实现该接口
 */
public interface TreeNode {
    // 获取节点名称
    String getName();

    // 获取节点父ID
    String getParentId();

    // 获取节点ID
    String getId();
}
