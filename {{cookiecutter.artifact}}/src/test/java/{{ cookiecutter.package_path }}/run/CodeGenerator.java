package {{ cookiecutter.basePackage }}.run;

import io.github.zxyle.generator.Generator;

import java.io.IOException;

/**
 * JPA 代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator("127.0.0.1:3306", "app", "root", "123456");
        generator.setTemplatePath("src/main/resources/templates/mybatis-plus");
        generator.setBasePackage(getBasePackage());
        generator.setOutPath("/biz");
        generator.run();
    }

    private static String getBasePackage() {
        Class<?> clazz = CodeGenerator.class;
        Package currentPackage = clazz.getPackage();
        String packageName = currentPackage.getName();
        return packageName.substring(0, packageName.lastIndexOf("."))
                .replace(".", "/");
    }

}
