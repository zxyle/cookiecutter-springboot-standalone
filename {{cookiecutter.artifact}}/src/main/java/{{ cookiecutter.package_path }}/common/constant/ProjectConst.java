package {{ cookiecutter.basePackage }}.common.constant;

/**
 * 项目常量
 */
public final class ProjectConst {

    private ProjectConst() {
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

    /**
     * 数据表 记录创建时间
     * 也可以采用这些命名： gmt_create
     */
    public static final String CREATE_FIELD = "create_time";

    /**
     * 数据表 记录创建人
     */
    public static final String CREATOR_FIELD = "create_by";

    /**
     * 数据表 记录更新时间
     * 也可以采用这些命名： gmt_modified、modify_time
     */
    public static final String UPDATE_FIELD = "update_time";

    /**
     * 数据表 记录更新人
     */
    public static final String UPDATER_FIELD = "update_by";

    /**
     * 逻辑删除字段
     */
    public static final String LOGIC_DELETE_FIELD = "deleted";

    /*逻辑删除 0未删除 1已删除*/
    public static final Integer LOGIC_NOT_DELETED = 0;
    public static final Integer LOGIC_DELETED = 1;

}
