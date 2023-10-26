# cookiecutter-springboot-standalone

## 项目介绍
本项目是基于[Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)、Redis、MySQL、[Mybatis Plus](https://baomidou.com/)、[Spring Security](https://docs.spring.io/spring-security/reference/index.html)等技术实现的后端快速开发脚手架。 
适合中小型项目快速搭建，具有提高开发效率，降低开发成本等特点。


## 集成功能
- 代码生成: 可以根据数据表生成`Entity`、`Controller`、`Mapper`、`Service`、`ServiceImpl`、`XML`等通用类
- 默认接口: `Controller` 默认已实现列表分页查询、单条数据新增、按ID查询更新删除、Excel数据导入导出和批量操作等9个常用接口
- 密码管理: 支持密码加盐散列、密码过期时间、弱密码检测、密码重试次数、历史密码、找回密码、修改密码、重置密码等
- 账号管理: 账号登录退出、账号启用停用、账号锁定、创建账号、注册账号、下线账号
- 登录管理: 支持账号密码登录、验证码登录、三方登录、验证器登录、扫码登录
- 权限认证: 已实现`RBAC0`权限模型（用户、用户组、角色、权限）
- 缓存管理: Caffeine + Redis 二级缓存
- 系统管理: 系统设置、登录日志、操作日志、友情链接、IP黑白名单、系统信息、意见反馈、发布版本、redis管理等
- 字典管理: 内置全国村社级行政区划信息（64万条地名）、性别、客户端类型、民族、常用邮箱、国家、证件类型、亲属关系等
- 系统监控: 心跳检测
- 系统告警: 支持钉钉Hook告警
- 二次验证: 支持图形验证码、短信验证码、邮件验证码
- 接口文档: 集成[smart-doc](https://smart-doc-group.github.io/#/zh-cn/)接口文档, 相比swagger具有侵入性低、团队管理方便、界面美观等特点
- 部署管理: 开机自启、安装包备份、安装包回滚
- 参数校验:
- 统一接口返回，统一异常处理


## 使用步骤
> 需系统安装[python3](https://www.python.org/downloads/)
```bash
$ pip3 install -U cookiecutter -i https://mirrors.aliyun.com/pypi/simple/
$ cookiecutter <repo_url> 
```

- 按提示输入项目信息，以完成项目创建（项目名称建议以-api结尾 如`shop-api`）
- 准备mysql和redis测试环境
- 修改`application.properties`、`application-dev.properties`、`CodeGenerator.java` 里的mysql、redis连接信息。
- 将`docs/ddl`目录下的sql文件导入数据库中
- 设计并创建业务数据表
- 运行test包下`CodeGenerator` 生成代码文件，可以删除不需要的类、方法、接口
- 开始编写具体业务逻辑代码


## 开源协议
MIT License
