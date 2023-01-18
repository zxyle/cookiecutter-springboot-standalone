# cookiecutter-springboot-standalone

## 项目介绍
基于SpringBoot、Redis、MySql、[MybatisPlus](https://baomidou.com/)、SpringSecurity等技术实现的后端快速开发脚手架。 适合中小型项目


## 集成功能
- 代码生成: 可以根据数据表生成`Entity`、`Controller`、`Mapper`、`Service`、`ServiceImpl`、`XML`等通用类
- 默认接口: `Controller` 默认已实现列表分页查询、单条数据新增、按ID查询更新删除、Excel数据导入导出等常用接口
- 密码管理: 支持密码加盐散列、密码过期时间、弱密码检测、密码重试次数
- 账号管理: 账号登录、账号退出、账号封禁、账号锁定、找回账户、找回密码等
- 权限认证: 已实现`RBAC0`权限模型（用户、用户组、角色、权限）
- 缓存管理: Caffeine + Redis 二级缓存
- 系统管理: 登录日志、操作日志、友情链接、IP黑白名单、系统信息
- 字典管理: 内置全国村社级行政区划信息（64万条地名）、性别、客户端类型、民族、星座等
- 系统监控: 心跳检测
- 系统告警: 支持钉钉Hook告警
- 验证码  : 支持图形验证码、短信验证码、邮件验证码
- 接口文档: 集成[smart-doc](https://smart-doc-group.github.io/#/zh-cn/)接口文档, 相比swagger侵入性低、团队管理方便、界面美观等特点
- 部署管理: 开机自启、安装包备份、安装包回滚
- 参数校验:
- 统一接口返回
- 部署管理： 开机自启、安装包备份、安装包回滚


## 使用步骤
> 需系统安装[python3](https://www.python.org/downloads/)
```bash
$ pip3 install -U cookiecutter -i https://mirrors.aliyun.com/pypi/simple/
$ cookiecutter https://github.com/zxyle/cookiecutter-springboot-standalone.git 
```

- 按提示输入项目信息，以完成项目创建（项目名称建议以-api结尾 如`shop-api`）
- 修改mysql、redis连接信息。如果没有可用环境，推荐使用docker-compose进行创建
- 将docs/ddl目录下的sql文件导入数据库
- 创建数据表
- 生成代码文件
- 开始编码

## 待完成清单
- [ ] 完善使用文档
- [ ] 后台管理页面开发
- [ ] 使用package by feature 形式组织文件
- [ ] sys模块功能开发


## 和其他快速开发平台的区别？
现在社区里有非常多的快速开发平台，如若依、jeetcg等，这些

优点：
- 包空间可以自定义
- 创建项目更多自有
- 全生命周期

缺点：
- 暂时没有界面

他们缺点
- 依赖较旧
- 知识产权


## 开源协议
MIT License
