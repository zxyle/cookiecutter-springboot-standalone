package {{ cookiecutter.basePackage }}.common.util;

import {{ cookiecutter.basePackage }}.biz.sys.response.AntdTree2;
import dev.zhengxiang.tool.utils.BoolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具类
 * 可用于用户组管理、菜单管理
 */
public class TreeUtil {

    /**
     * 创建树
     *
     * @param list     节点列表
     * @param parentId 父节点ID
     * @return antd风格树结构
     */
    public static <E extends TreeNode> List<AntdTree2> createTree(List<E> list, String parentId) {
        // 获取所有子节点
        List<AntdTree2> childTreeList = getChildTree(list, parentId);
        for (AntdTree2 tree : childTreeList) {
            List<AntdTree2> children = createTree(list, tree.getValue());
            if (BoolUtil.bool(children)) {
                tree.setChildren(children);
            }
        }
        return childTreeList;
    }

    public static <E extends TreeNode> List<AntdTree2> getChildTree(List<E> list, String id) {
        List<AntdTree2> children = new ArrayList<>();
        list.forEach(e -> {
            if (e.getParentId().equals(id)) {
                AntdTree2 antdTree = new AntdTree2(e.getName(), e.getId());
                children.add(antdTree);
            }
        });
        return children;
    }

    /**
     * 合并树
     */
    public static void mergeTree(TreeNode father, TreeNode children) {
        //
        String parentId = children.getParentId();

        // 递归寻找父亲树的。然后setChildren

    }

    /**
     * 图形化显示树
     *
     * @param tree
     */
    public static void printTree(TreeNode tree) {

    }


    public static List<String> getChildElements(AntdTree2 tree, List<String> elements) {
        List<? extends AntdTree2> children = tree.getChildren();
        if (children != null) {
            for (AntdTree2 tree2 : children) {
                elements.add(tree2.getValue());
                getChildElements(tree2, elements);
            }
        }
        return elements;
    }


    public static List<String> getAll(AntdTree2 tree) {
        List<String> elements = new ArrayList<>();
        return getChildElements(tree, elements);
    }
}

