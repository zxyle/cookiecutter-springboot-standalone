package {{ cookiecutter.basePackage }}.biz.sys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class RuoyiController {

    @GetMapping(value = "/getInfo", produces = "application/json")
    public String test() {
        return "{\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"code\": 200,\n" +
                "  \"permissions\": [\n" +
                "    \"*:*:*\"\n" +
                "  ],\n" +
                "  \"roles\": [\n" +
                "    \"admin\"\n" +
                "  ],\n" +
                "  \"user\": {\n" +
                "    \"createBy\": \"admin\",\n" +
                "    \"createTime\": \"2024-06-30 11:27:11\",\n" +
                "    \"updateBy\": null,\n" +
                "    \"updateTime\": null,\n" +
                "    \"remark\": \"管理员\",\n" +
                "    \"userId\": 1,\n" +
                "    \"deptId\": 103,\n" +
                "    \"userName\": \"admin\",\n" +
                "    \"nickName\": \"若依\",\n" +
                "    \"email\": \"ry@163.com\",\n" +
                "    \"phonenumber\": \"15888888888\",\n" +
                "    \"sex\": \"1\",\n" +
                "    \"avatar\": \"\",\n" +
                "    \"password\": \"$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2\",\n" +
                "    \"status\": \"0\",\n" +
                "    \"delFlag\": \"0\",\n" +
                "    \"loginIp\": \"220.246.88.155\",\n" +
                "    \"loginDate\": \"2024-09-02T12:32:35.000+08:00\",\n" +
                "    \"dept\": {\n" +
                "      \"createBy\": null,\n" +
                "      \"createTime\": null,\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": null,\n" +
                "      \"deptId\": 103,\n" +
                "      \"parentId\": 101,\n" +
                "      \"ancestors\": \"0,100,101\",\n" +
                "      \"deptName\": \"研发部门\",\n" +
                "      \"orderNum\": 1,\n" +
                "      \"leader\": \"若依\",\n" +
                "      \"phone\": null,\n" +
                "      \"email\": null,\n" +
                "      \"status\": \"0\",\n" +
                "      \"delFlag\": null,\n" +
                "      \"parentName\": null,\n" +
                "      \"children\": []\n" +
                "    },\n" +
                "    \"roles\": [\n" +
                "      {\n" +
                "        \"createBy\": null,\n" +
                "        \"createTime\": null,\n" +
                "        \"updateBy\": null,\n" +
                "        \"updateTime\": null,\n" +
                "        \"remark\": null,\n" +
                "        \"roleId\": 1,\n" +
                "        \"roleName\": \"超级管理员\",\n" +
                "        \"roleKey\": \"admin\",\n" +
                "        \"roleSort\": 1,\n" +
                "        \"dataScope\": \"1\",\n" +
                "        \"menuCheckStrictly\": false,\n" +
                "        \"deptCheckStrictly\": false,\n" +
                "        \"status\": \"0\",\n" +
                "        \"delFlag\": null,\n" +
                "        \"flag\": false,\n" +
                "        \"menuIds\": null,\n" +
                "        \"deptIds\": null,\n" +
                "        \"permissions\": null,\n" +
                "        \"admin\": true\n" +
                "      }\n" +
                "    ],\n" +
                "    \"roleIds\": null,\n" +
                "    \"postIds\": null,\n" +
                "    \"roleId\": null,\n" +
                "    \"admin\": true\n" +
                "  }\n" +
                "}";
    }


    @GetMapping(value = "/getRouters", produces = "application/json")
    public String getRouters() {
        return "{\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"code\": 200,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"name\": \"System\",\n" +
                "      \"path\": \"/system\",\n" +
                "      \"hidden\": false,\n" +
                "      \"redirect\": \"noRedirect\",\n" +
                "      \"component\": \"Layout\",\n" +
                "      \"alwaysShow\": true,\n" +
                "      \"meta\": {\n" +
                "        \"title\": \"系统管理\",\n" +
                "        \"icon\": \"system\",\n" +
                "        \"noCache\": false,\n" +
                "        \"link\": null\n" +
                "      },\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"User\",\n" +
                "          \"path\": \"user\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/user/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"用户管理\",\n" +
                "            \"icon\": \"user\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Role\",\n" +
                "          \"path\": \"role\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/role/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"角色管理\",\n" +
                "            \"icon\": \"peoples\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Menu\",\n" +
                "          \"path\": \"menu\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/menu/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"菜单管理\",\n" +
                "            \"icon\": \"tree-table\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Dept\",\n" +
                "          \"path\": \"dept\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/dept/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"部门管理\",\n" +
                "            \"icon\": \"tree\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Post\",\n" +
                "          \"path\": \"post\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/post/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"岗位管理\",\n" +
                "            \"icon\": \"post\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Dict\",\n" +
                "          \"path\": \"dict\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/dict/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"字典管理\",\n" +
                "            \"icon\": \"dict\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Config\",\n" +
                "          \"path\": \"config\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/config/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"参数设置\",\n" +
                "            \"icon\": \"edit\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Notice\",\n" +
                "          \"path\": \"notice\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/notice/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"通知公告\",\n" +
                "            \"icon\": \"message\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Log\",\n" +
                "          \"path\": \"log\",\n" +
                "          \"hidden\": false,\n" +
                "          \"redirect\": \"noRedirect\",\n" +
                "          \"component\": \"ParentView\",\n" +
                "          \"alwaysShow\": true,\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"日志管理\",\n" +
                "            \"icon\": \"log\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          },\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"name\": \"Operlog\",\n" +
                "              \"path\": \"operlog\",\n" +
                "              \"hidden\": false,\n" +
                "              \"component\": \"monitor/operlog/index\",\n" +
                "              \"meta\": {\n" +
                "                \"title\": \"操作日志\",\n" +
                "                \"icon\": \"form\",\n" +
                "                \"noCache\": false,\n" +
                "                \"link\": null\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"Logininfor\",\n" +
                "              \"path\": \"logininfor\",\n" +
                "              \"hidden\": false,\n" +
                "              \"component\": \"monitor/logininfor/index\",\n" +
                "              \"meta\": {\n" +
                "                \"title\": \"登录日志\",\n" +
                "                \"icon\": \"logininfor\",\n" +
                "                \"noCache\": false,\n" +
                "                \"link\": null\n" +
                "              }\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Monitor\",\n" +
                "      \"path\": \"/monitor\",\n" +
                "      \"hidden\": false,\n" +
                "      \"redirect\": \"noRedirect\",\n" +
                "      \"component\": \"Layout\",\n" +
                "      \"alwaysShow\": true,\n" +
                "      \"meta\": {\n" +
                "        \"title\": \"系统监控\",\n" +
                "        \"icon\": \"monitor\",\n" +
                "        \"noCache\": false,\n" +
                "        \"link\": null\n" +
                "      },\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"Online\",\n" +
                "          \"path\": \"online\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"monitor/online/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"在线用户\",\n" +
                "            \"icon\": \"online\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Job\",\n" +
                "          \"path\": \"job\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"monitor/job/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"定时任务\",\n" +
                "            \"icon\": \"job\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Druid\",\n" +
                "          \"path\": \"druid\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"monitor/druid/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"数据监控\",\n" +
                "            \"icon\": \"druid\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Server\",\n" +
                "          \"path\": \"server\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"monitor/server/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"服务监控\",\n" +
                "            \"icon\": \"server\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Cache\",\n" +
                "          \"path\": \"cache\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"monitor/cache/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"缓存监控\",\n" +
                "            \"icon\": \"redis\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"CacheList\",\n" +
                "          \"path\": \"cacheList\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"monitor/cache/list\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"缓存列表\",\n" +
                "            \"icon\": \"redis-list\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Tool\",\n" +
                "      \"path\": \"/tool\",\n" +
                "      \"hidden\": false,\n" +
                "      \"redirect\": \"noRedirect\",\n" +
                "      \"component\": \"Layout\",\n" +
                "      \"alwaysShow\": true,\n" +
                "      \"meta\": {\n" +
                "        \"title\": \"系统工具\",\n" +
                "        \"icon\": \"tool\",\n" +
                "        \"noCache\": false,\n" +
                "        \"link\": null\n" +
                "      },\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"Build\",\n" +
                "          \"path\": \"build\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"tool/build/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"表单构建\",\n" +
                "            \"icon\": \"build\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Gen\",\n" +
                "          \"path\": \"gen\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"tool/gen/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"代码生成\",\n" +
                "            \"icon\": \"code\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Person\",\n" +
                "          \"path\": \"person\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"system/person/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"Person\",\n" +
                "            \"icon\": \"swagger\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Swagger\",\n" +
                "          \"path\": \"swagger\",\n" +
                "          \"hidden\": false,\n" +
                "          \"component\": \"tool/swagger/index\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"系统接口\",\n" +
                "            \"icon\": \"swagger\",\n" +
                "            \"noCache\": false,\n" +
                "            \"link\": null\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @GetMapping(value = "/system/config/configKey/sys.user.initPassword", produces = "application/json")
    public String getConfigKey() {
        return "{\n" +
                "  \"msg\": \"123456\",\n" +
                "  \"code\": 200\n" +
                "}";
    }

    @GetMapping(value = "/system/dict/data/type/sys_user_sex", produces = "application/json")
    public String getConfigKey2() {
        return "{\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"code\": 200,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-07-03 20:28:55\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"性别男\",\n" +
                "      \"params\": {\n" +
                "        \"@type\": \"java.util.HashMap\"\n" +
                "      },\n" +
                "      \"dictCode\": 1,\n" +
                "      \"dictSort\": 1,\n" +
                "      \"dictLabel\": \"男\",\n" +
                "      \"dictValue\": \"0\",\n" +
                "      \"dictType\": \"sys_user_sex\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"\",\n" +
                "      \"isDefault\": \"Y\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-07-03 20:28:55\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"性别女\",\n" +
                "      \"params\": {\n" +
                "        \"@type\": \"java.util.HashMap\"\n" +
                "      },\n" +
                "      \"dictCode\": 2,\n" +
                "      \"dictSort\": 2,\n" +
                "      \"dictLabel\": \"女\",\n" +
                "      \"dictValue\": \"1\",\n" +
                "      \"dictType\": \"sys_user_sex\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-07-03 20:28:55\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"性别未知\",\n" +
                "      \"params\": {\n" +
                "        \"@type\": \"java.util.HashMap\"\n" +
                "      },\n" +
                "      \"dictCode\": 3,\n" +
                "      \"dictSort\": 3,\n" +
                "      \"dictLabel\": \"未知\",\n" +
                "      \"dictValue\": \"2\",\n" +
                "      \"dictType\": \"sys_user_sex\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
    //
    @GetMapping(value = "/system/dict/data/type/sys_normal_disable", produces = "application/json")
    public String getConfigKey3() {
        return "{\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"code\": 200,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-07-03 20:28:55\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"正常状态\",\n" +
                "      \"params\": {\n" +
                "        \"@type\": \"java.util.HashMap\"\n" +
                "      },\n" +
                "      \"dictCode\": 6,\n" +
                "      \"dictSort\": 1,\n" +
                "      \"dictLabel\": \"正常\",\n" +
                "      \"dictValue\": \"0\",\n" +
                "      \"dictType\": \"sys_normal_disable\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"primary\",\n" +
                "      \"isDefault\": \"Y\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-07-03 20:28:55\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"停用状态\",\n" +
                "      \"params\": {\n" +
                "        \"@type\": \"java.util.HashMap\"\n" +
                "      },\n" +
                "      \"dictCode\": 7,\n" +
                "      \"dictSort\": 2,\n" +
                "      \"dictLabel\": \"停用\",\n" +
                "      \"dictValue\": \"1\",\n" +
                "      \"dictType\": \"sys_normal_disable\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"danger\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @GetMapping(value = "/system/dict/data/type/sys_oper_type", produces = "application/json")
    public String xxx() {
        return "{\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"code\": 200,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:24\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"新增操作\",\n" +
                "      \"dictCode\": 19,\n" +
                "      \"dictSort\": 1,\n" +
                "      \"dictLabel\": \"新增\",\n" +
                "      \"dictValue\": \"1\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"info\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:25\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"修改操作\",\n" +
                "      \"dictCode\": 20,\n" +
                "      \"dictSort\": 2,\n" +
                "      \"dictLabel\": \"修改\",\n" +
                "      \"dictValue\": \"2\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"info\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:25\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"删除操作\",\n" +
                "      \"dictCode\": 21,\n" +
                "      \"dictSort\": 3,\n" +
                "      \"dictLabel\": \"删除\",\n" +
                "      \"dictValue\": \"3\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"danger\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:25\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"授权操作\",\n" +
                "      \"dictCode\": 22,\n" +
                "      \"dictSort\": 4,\n" +
                "      \"dictLabel\": \"授权\",\n" +
                "      \"dictValue\": \"4\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"primary\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:25\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"导出操作\",\n" +
                "      \"dictCode\": 23,\n" +
                "      \"dictSort\": 5,\n" +
                "      \"dictLabel\": \"导出\",\n" +
                "      \"dictValue\": \"5\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"warning\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:26\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"导入操作\",\n" +
                "      \"dictCode\": 24,\n" +
                "      \"dictSort\": 6,\n" +
                "      \"dictLabel\": \"导入\",\n" +
                "      \"dictValue\": \"6\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"warning\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:26\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"强退操作\",\n" +
                "      \"dictCode\": 25,\n" +
                "      \"dictSort\": 7,\n" +
                "      \"dictLabel\": \"强退\",\n" +
                "      \"dictValue\": \"7\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"danger\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:26\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"生成操作\",\n" +
                "      \"dictCode\": 26,\n" +
                "      \"dictSort\": 8,\n" +
                "      \"dictLabel\": \"生成代码\",\n" +
                "      \"dictValue\": \"8\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"warning\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:26\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"清空操作\",\n" +
                "      \"dictCode\": 27,\n" +
                "      \"dictSort\": 9,\n" +
                "      \"dictLabel\": \"清空数据\",\n" +
                "      \"dictValue\": \"9\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"danger\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:24\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"其他操作\",\n" +
                "      \"dictCode\": 18,\n" +
                "      \"dictSort\": 99,\n" +
                "      \"dictLabel\": \"其他\",\n" +
                "      \"dictValue\": \"0\",\n" +
                "      \"dictType\": \"sys_oper_type\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"info\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @GetMapping(value = "/system/dict/data/type/sys_common_status", produces = "application/json")
    public String getConfigKey32() {
        return "{\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"code\": 200,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:27\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"正常状态\",\n" +
                "      \"dictCode\": 28,\n" +
                "      \"dictSort\": 1,\n" +
                "      \"dictLabel\": \"成功\",\n" +
                "      \"dictValue\": \"0\",\n" +
                "      \"dictType\": \"sys_common_status\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"primary\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"createBy\": \"admin\",\n" +
                "      \"createTime\": \"2024-06-30 11:28:27\",\n" +
                "      \"updateBy\": null,\n" +
                "      \"updateTime\": null,\n" +
                "      \"remark\": \"停用状态\",\n" +
                "      \"dictCode\": 29,\n" +
                "      \"dictSort\": 2,\n" +
                "      \"dictLabel\": \"失败\",\n" +
                "      \"dictValue\": \"1\",\n" +
                "      \"dictType\": \"sys_common_status\",\n" +
                "      \"cssClass\": \"\",\n" +
                "      \"listClass\": \"danger\",\n" +
                "      \"isDefault\": \"N\",\n" +
                "      \"status\": \"0\",\n" +
                "      \"default\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

}
