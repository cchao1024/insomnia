# Insomnia

Insomnia-server 是全栈项目 **Insomnia** 的服务端代码，项目基于 SpringBoot 开发
整合 **jwt，mysql，Spring Jpa，Redis，Freemarker**等主流后端开发框架
本地环境基于 **docker-compose** 部署。

 * [insomnia-server springBoot后端项目](https://github.com/cchao1024/insomnia-server)
 * [insomnia-android android 客户端](https://github.com/cchao1024/insomnia-android)
 * [simpleLib android 基础类库](https://github.com/cchao1024/insomnia-server)

## 项目结构
```
Insomnia-server
├── insomnia_admin 后台管理系统
├── insomnia_api 为前端app提供 接口
└── insomnia_common 通用的响应和常量
```
项目为多模块构建，**后台管理admin** 依赖于 api模块，拿到后者提供的接口数据，交由 **Freemarker** 模板引擎填充展示。
common模块 则放置一些通用的类库和常量

## 主要功能
```
Insomnia-server
├── 用户相关
|   ├── 游客邮箱绑定
|   ├── 用户登录
|   ├── 收藏列表
|   └── 信息查看/修改 头像上传
|
├── 图片相关
|   ├── 图片列表
|   └── 图片查看、收藏、下载 
|
├── 音乐相关
|   ├── 音乐列表
|   └── 音乐播放、收藏、下载 
|
└── 说说
    ├── 说说图片上传  
    ├── 发布说说
    ├─- 评论/点赞说说
    └─- 回复/点赞他人评论及回复
      
```

## 后台管理
通过笔者远程地址可以预览 [http://47.240.35.14:8080/admin/fall_image/list](http://47.240.35.14:8080/admin/fall_image/list) 

完成本地部署(文末有部署步骤)后可以通过 [localhost:8080/admin/index](localhost:8080/admin/index) 查看，

大概长这样子：
![](https://raw.githubusercontent.com/cchao1024/insomnia-server/master/document/admin_1.jpg) 
![](https://raw.githubusercontent.com/cchao1024/insomnia-server/master/document/admin_2.jpg) 
![](https://raw.githubusercontent.com/cchao1024/insomnia-server/master/document/admin_3.jpg) 


* Android 端 App 已实现基本功能，项目地址:[cchao1024/insomnia-android](https://github.com/cchao1024/insomnia-android)

# 依赖框架
技术 | 说明 | 官网
----|----|----
Spring Boot | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
Spring Jpa | 持久层API | [https://spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa)
RabbitMQ | 消息队列 | [https://www.rabbitmq.com/getstarted.html](https://www.rabbitmq.com/getstarted.html)
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
**1 配置 docker-compose 运行环境**

 可以参见笔者博文 [https://cchao1024.github.io/Docker解放生产力](https://cchao1024.github.io/2019-06-Docker%20%E8%A7%A3%E6%94%BE%E7%94%9F%E4%BA%A7%E5%8A%9B/)

**2 搭建开发环境**

将项目中 **document/docker-compose.yaml** 复制到你期望的 docker 目录下，执行

```c
docker-compose up
```
**3 导入数据库**
 
将项目中 **document/backup.sql** 复制到你的 docker 目录下的 **mysql/data**(这个目录会映射到 mysql 容器中，docker-compose.yaml配置文件中有说明的)
 执行 数据库还原
 
 ```$xslt
 1 docker-compose exec mysql bash    # 进入docker终端
 2 mysql -u root -pROOT
 3 create database insomnia;
 4 source /var/lib/mysql/backup.sql
 ```
这样通过本机的 **3306** 端口就会看到 已经恢复的 insomnia 数据库
**4 运行** 

 使用 IDEA 打开 insomnia-server 项目并运行，通过 **localhost:8080/admin/index** 进入后台。

**5 消息队列部署**

这一项不是必选的，说说被小伙伴点赞后会发送一条推送消息（基于JPush）到说说用户的设备上
笔者将这个流程放入**消息队列**，交由**消息消费者系统**去负责发送这些推送，也算是分布式应用了
如果需要这个环节，需要 clone [这个项目](https://github.com/cchao1024/MsgQueueHandler.git)来运行，后面也会将邮箱验证发送放入消息队列
# TODO 
1. 如果进行顺利，加入个 **睡不着？起来嗨** 模块，放一下 **刺激的，引人深思** 的文章或图片或视频
2. 加入 Banner 超链接，提供一些七七八八的文章
3. 消息队列放入邮箱验证，由消息消费者系统分布式处理