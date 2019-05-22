# Insomnia

Insomnia-server 是全栈项目 ** Insomnia ** 的服务端代码，项目基于 SpringBoot 开发

## 项目结构
```
Insomnia-server
├── insomnia_admin 后台管理系统
├── insomnia_api 为前端app提供 接口
└── insomnia_common 通用的响应和常量
```

## 主要功能
```
Insomnia-server
├── 用户相关
|   ├── 游客邮箱绑定
|   ├── 用户登录
|   └── 信息查看/修改
|
├── 催眠图片
|   ├── 图片列表
|   └── 图片查看、点赞、下载 
|
├── 音乐相关
|   ├── 音乐列表
|   └── 音乐播放、点赞、下载 
|
└── 说说
    ├── 说说图片上传  
    ├── 发布说说
    ├─- 评论/点赞说说
    └─- 回复/点赞他人评论及回复  
```

## 其他端
* 项目后台 运行 insomnia_admin 模块后，进入 [localhost:8080/admin/index](localhost:8080/admin/index) 查看
* Android 端 App 已实现基本功能，项目地址:[cchao1024/insomnia-android](https://github.com/cchao1024/insomnia-android)
* Web 端处于开发阶段。


# 依赖框架
技术 | 说明 | 官网
----|----|----
Spring Boot | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
Spring Security | 认证和授权框架 | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)
Spring Jpa | 持久层API | [https://spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa)
Swagger-UI | 文档生产工具 | [https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui)
javax.validation | 验证框架 | [javax.validation](https://docs.oracle.com/javaee/7/api/javax/validation/package-summary.html)
Freemarker | 模板引擎 | [https://freemarker.apache.org/](https://freemarker.apache.org/)
Redis | 分布式缓存 | [https://redis.io/](https://redis.io/)
Mysql | 数据库 | [https://www.mysql.com/](https://www.mysql.com/)
Docker | 应用容器引擎 | [https://www.docker.com/](https://www.docker.com/)
Druid | 数据库连接池 | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
COS | 对象存储 | [https://cloud.tencent.com](https://cloud.tencent.com)
JWT | JWT登录支持 | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt)
Lombok | 简化对象封装工具 | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok)

# 本地环境搭建
运行本地环境仅需配置 mysql 数据库即可
