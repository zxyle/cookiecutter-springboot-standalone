package {{ cookiecutter.basePackage }}.snippet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import {{ cookiecutter.basePackage }}.biz.site.realname.IdTypeEnum;
import {{ cookiecutter.basePackage }}.biz.sys.dict.Dict;
import {{ cookiecutter.basePackage }}.biz.sys.dict.DictService;
import io.github.zxyle.codegen.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典表与枚举互相转换
 */
@SpringBootTest
public class TestDict {

    @Autowired
    DictService dictService;


    /**
     * 将枚举类的值转换为字典
     */
    @Test
    void saveDict() {
        String name = "证件类型2";
        String dictType = "id_type2";
        List<Dict> dicts = new ArrayList<>(IdTypeEnum.values().length);
        int i = 0;
        for (IdTypeEnum value : IdTypeEnum.values()) {
            Dict dict = new Dict();
            dict.setName(name);
            dict.setDictSort(i);
            dict.setDictType(dictType);
            dict.setLabel(value.getName());
            dict.setValue(String.valueOf(value.getCode()));
            dicts.add(dict);
            i++;
        }

        System.out.println(dicts);

        // 是否先清空
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", dictType);
        dictService.remove(wrapper);

        dictService.saveBatch(dicts);
    }

    /**
     * 将字典转换为枚举类
     */
    @Test
    void saveEnum() {
        String dictType = "id_type";
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", dictType);
        List<Dict> dicts = dictService.list(wrapper);

        System.out.println("import com.baomidou.mybatisplus.annotation.EnumValue;\n" +
                "import com.fasterxml.jackson.annotation.JsonValue;\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" +
                "import lombok.ToString;\n");

        System.out.println("/**\n" +
                " * " + dicts.get(0).getName() + "枚举（用于实体类属性）\n" +
                " */\n");

        System.out.println("@Getter\n" +
                "@ToString\n" +
                "@AllArgsConstructor\n");

        String className = StringUtil.toCapitalize(StringUtil.toCamelCase(dicts.get(0).getDictType()));
        System.out.println("public enum " + className + "Enum {\n");

        for (int i = 0; i < dicts.size(); i++) {
            Dict dict = dicts.get(i);
            System.out.println("    " + "Value"+ i+ "(" + dict.getValue() + ", \"" + dict.getLabel() + "\"),");
        }

        System.out.println("\n" +
                "    /**\n" +
                "     * 用于数据库存储的值\n" +
                "     */\n" +
                "    @EnumValue\n" +
                "    private final int code;\n" +
                "\n" +
                "    /**\n" +
                "     * 用于前端展示的值\n" +
                "     */\n" +
                "    @JsonValue\n" +
                "    private final String name;\n" +
                "\n" +
                "}");
    }
}
