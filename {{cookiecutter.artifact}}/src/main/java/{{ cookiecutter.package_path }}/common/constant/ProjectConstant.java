package {{ cookiecutter.basePackage }}.common.constant;

/**
 * 项目常量
 */
public final class ProjectConstant {

    private ProjectConstant() {

    }

    /**
     * 生成代码所在的基础包名称，可根据自己公司的项目修改
     * 注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类
     */
    public static final String BASE_PACKAGE = "{{ cookiecutter.basePackage }}";

    public static final String PROD = "prod";

    public static final String Y = "Y";
    public static final String N = "N";

    public static final String ZERO = "0";
    public static final String DEFAULT_IP = "0:0:0:0:0:0:0:1";


}
