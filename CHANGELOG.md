# 变更日志

## v1.5.0
- 增加账号 + 验证码登录功能
- 增加参数默认值和字符串去空格等功能注解
- 增加权限请求API功能
- 补充代码文档、升级依赖版本等

## v1.4.1
- 完善类注释、接口文档、对接文档等
- 系统里配置信息由数据库设置驱动
- 删除sample模块
- 解决一些code smell

## v1.4.0
- 用户注册增加自动登录功能 [#38](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/38)
- 完善前端对接文档
- 分页接口和Excel导出接口二合一
- 优化登录接口
- 增加用户修改密码重试次数过多后锁定功能
- 增加注册时间和最后登录时间功能

## v1.3.0
- 清理无关mapper
- user_group 增加管理员功能支持
- 字典接口增加多dictType查询
- 增加操作日志支持
- 增加前端对接文档
- 增加初次登录修改密码支持 [#37](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/37)
- 完全用户组管理员权限控制
- 对手机号和邮箱进行脱敏
- 增加IP黑白名单管理 [#42](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/42)
- 验证码300秒有效期内不再发送
- 增加系统设置功能
- 完善换绑 [#39](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/39)

## v1.2.0


## v1.1.2
- 更换密码加盐工具
- 增加百分比计算工具类
- 补充代码注释信息
- 统一常量类命名
- 修复部署脚本中的错误
- 增加远程调试的支持
- 升级spring boot版本至 2.7.2 和2.6.10

### 变更概览
- 完善controller模板、通用权限使用
- 完善全局异常处理
- 完善权限系统初始化数据

## v1.1.1

### 变更概览
- 完善controller模板、通用权限使用
- 完善全局异常处理
- 完善权限系统初始化数据

## v1.1.0

### 变更概览
- 通用权限码设计与开发 [#3](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/3)
- 日志增加traceId [#10](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/10)
- 升级spring boot版本2.7.1和2.6.9 [#9](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/9) 
- 添加常用正则表达式 [#11](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/11)
- 完善验证码接口返回信息 [#12](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/12)
- 完善友链相关接口 [#13](https://github.com/zxyle/cookiecutter-springboot-standalone/issues/13)

## v1.0.0

### 变更概览
- 支持代码生成
- 支持完整RBAC0模型
- 支持RESTful风格接口生成
- 集成缓存、参数校验
- 集成smart-doc接口文档
