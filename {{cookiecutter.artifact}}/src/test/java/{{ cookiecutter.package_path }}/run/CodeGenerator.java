package {{ cookiecutter.basePackage }}.run;

import freemarker.template.TemplateException;
import io.github.zxyle.codegen.Generator;
import io.github.zxyle.codegen.table.Temp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 业务代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        Generator generator = new Generator("jdbc:mysql://localhost:3306/{{ cookiecutter.artifact.replace('-api', '') }}", "{{ cookiecutter.artifact.replace('-api', '') }}", "root", "123456");
        generator.setTemplatePath("src/main/resources/templates/mybatis-plus");

        // 需渲染的模板
        List<Temp> temps = Arrays.asList(
                new Temp("controller.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Controller.java"),
                new Temp("entity.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}.java"),
                new Temp("mapper.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Mapper.java"),
                new Temp("mapper.xml.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Mapper.xml"),
                new Temp("service.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Service.java"),
                new Temp("request.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Request.java"),
                new Temp("response.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Response.java"),
                new Temp("pageRequest.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}PageRequest.java"),
                new Temp("export.java.ftl", "src/main/java/${pkg}/biz/${biz}/${feat}/${className}Export.java")
        );
        generator.setTemps(temps);

        generator.setAbsPath(System.getProperty("user.dir")); // 项目根目录绝对路径
        String pkg = getBasePackage();
        generator.setPkg(pkg);
        generator.setBasePackage(pkg.replace("/", "."));
        generator.setBaseEntityPath("src/main/java/${pkg}/common/entity/BaseEntity.java");
        generator.setBaseControllerPath("src/main/java/${pkg}/common/controller/AuthBaseController.java");
        generator.setIgnoreColumns("id", "create_time", "update_time");
        generator.setEnableExcel(false); // default false
        generator.run();
    }

    private static String getBasePackage() {
        // 获取当前类的 Class 对象
        Class<?> clazz = CodeGenerator.class;

        // 获取当前类所在的包
        Package currentPackage = clazz.getPackage();

        // 获取当前包的名称
        String packageName = currentPackage.getName();

        // 获取父包的路径
        return packageName.substring(0, packageName.lastIndexOf(".")).replace(".", "/");
    }

}