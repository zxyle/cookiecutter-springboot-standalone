# cookiecutter-springboot-standalone

## 项目介绍
基于springboot、mybatisPlus、spring security、cookiecutter等技术实现的后端快速开发脚手架。


## 集成功能
- 代码生成: 根据数据表快速生成`Entity`、`Controller`、`Mapper`、`Service`、`XML`等通用类
- RESTful: `Controller` 默认已经实现列表分页查询、新增、按ID查询更新删除、Excel数据导入导出等接口
- 权限认证: 实现RBAC模型 JWT
- 缓存： 
- 系统管理： 
- 监控
- 告警：支持钉钉Hook告警
- 字典管理、涵盖中国村社级行政区划
- 图形验证码、短信验证码
- 接口文档： 集成smart-doc接口文档, 相比swagger侵入性低、团队管理方便
- 参数校验
- 统一接口返回
- 部署管理： 自动重启、安装包备份、安装包回滚


## 使用步骤
> 需系统安装python3
```bash
$ pip3 install -U cookiecutter -i https://mirrors.aliyun.com/pypi/simple/
$ cookiecutter https://jihulab.com/zxyle/cookiecutter-springboot-standalone.git 
```

- 按提示输入项目信息，以完成项目创建（项目名称建议以-api结尾 如highway-api）
- 修改mysql、redis连接信息 如果没有环境 可以使用docker-compose进行创建
- 将docs/ddl目录下的sql文件导入数据库
- Enjoy code

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
