package {{ cookiecutter.basePackage }}.biz.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Area;
import {{ cookiecutter.basePackage }}.biz.sys.response.AntdTree2;
import {{ cookiecutter.basePackage }}.biz.sys.service.IAreaService;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.util.AreaNode;
import {{ cookiecutter.basePackage }}.common.util.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 行政区划接口
 */
@RestController
@RequestMapping("/sys/area")
@Slf4j
public class AreaController {

    IAreaService thisService;

    public AreaController(IAreaService thisService) {
        this.thisService = thisService;
    }

    /**
     * 获取中国行政区划树状结构
     *
     * @param rootId 根节点ID
     * @param level  1-国家 2-省级 3-市级 4-县区级 5-镇街级 6-社区、村级
     */
    @GetMapping("/tree")
    @Cacheable(cacheNames = "areaCache", key = "#rootId+#level")
    public ApiResponse<AntdTree2> tree(
            @RequestParam(value = "rootId", required = false, defaultValue = "0000") String rootId,
            @RequestParam(value = "level", required = false, defaultValue = "4") Integer level) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.select("code, name, parent, level");
        wrapper.le("level", level);
        String rootName = "中国";
        String rootCode = "0000";
        Integer rootLevel = 1;
        if (!rootId.equals("0000")) {
            wrapper.likeRight("code", rootId);
            Area rootArea = thisService.getAreaByCode(rootId);
            rootName = rootArea.getName();
            rootCode = rootArea.getCode();
            rootLevel = rootArea.getLevel();
        }

        if ((level - rootLevel) > 3) {
            log.info("查询超过层级限制");
            return new ApiResponse<>("查询超过层级限制", false);
        }

        List<Area> areas = thisService.list(wrapper);
        List<AreaNode> nodes = areas.stream().map(e -> {
            AreaNode node = new AreaNode();
            node.setId(e.getCode());
            node.setName(e.getName());
            node.setParentId(e.getParent());
            return node;
        }).collect(Collectors.toList());

        AntdTree2 tree = new AntdTree2();
        List<AntdTree2> listTree = TreeUtil.createTree(nodes, rootId);
        tree.setValue(rootCode);
        tree.setLabel(rootName);
        tree.setChildren(listTree);
        return new ApiResponse<>(tree);
    }
}
