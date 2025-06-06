package {{ cookiecutter.basePackage }}.biz.sys.area;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.response.AntdTree2;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import {{ cookiecutter.basePackage }}.common.response.AreaNode;
import {{ cookiecutter.basePackage }}.common.util.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import {{ cookiecutter.namespace }}.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 行政区划接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/area")
public class AreaController {

    final AreaService thisService;

    /**
     * 获取中国行政区划树状结构
     */
    @GetMapping("/tree")
    @Cacheable(cacheNames = "AreaCache", key = "#req.rootId+#req.level")
    public R<AntdTree2> tree(@Valid AreaRequest req) {
        String rootId = req.getRootId();
        Integer level = req.getLevel();
        LambdaQueryWrapper<Area> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Area::getCode, Area::getName, Area::getParentId, Area::getLevel);
        wrapper.le(Area::getLevel, level);
        String rootName = "中国";
        String rootCode = "0000";
        Integer rootLevel = 1;
        if (!"0000".equals(rootId)) {
            wrapper.likeRight(Area::getCode, rootId);
            Area rootArea = thisService.findAreaByCode(rootId);
            if (rootArea != null) {
                rootName = rootArea.getName();
                rootCode = rootArea.getCode();
                rootLevel = rootArea.getLevel();
            }
        }

        if ((level - rootLevel) > 3) {
            return R.fail("查询超过层级限制");
        }

        List<Area> areas = thisService.list(wrapper);
        List<AreaNode> nodes = areas.stream()
                .map(node -> new AreaNode(node.getName(), node.getParentId(), node.getCode()))
                .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};

        List<AntdTree2> listTree = TreeUtil.createTree(nodes, rootId);
        AntdTree2 tree = new AntdTree2(rootName, rootCode, listTree);
        return R.ok(tree);
    }
}
