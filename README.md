easy-starters是一个公共starter集合,旨在帮助java开发者快速构建springboot项目.

## lx-starters的环境要求

JDK requirement: JDK 1.8+

Spring booter 2.x,+



## 如何引用lx-starters中的模块

目前只支持以maven坐标的形式导入, 以下以 common-starter 模块示例, 需要提前将本项目install到本地仓库。

* 导入maven坐标即可

```java
<dependency>
    <groupId>com.lx</groupId>
    <artifactId>common-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

然后你就可以直接在你的项目中直接使用common-starter中的内建功能了.

## lx-starters中的模块的作用

* common-starter

    * 提供了统一化的公共的返回前台对象以及其工具类

    * 提供了统一化管理springIOC容器的工具类

    * 提供了新的日期转换器

    * 提供了默认的全局异常处理

    * 提供了大量常用的工具类

      ......

* feign-starter

    * 提供了在使用feign调用其他服务时的服务寻址策略

* mapper-starter

    * 内建tk.mybatis的基础通用类,直接继承即可使用tk.mybatis的方式来操作数据库
    * 内建JdbcTemplateUtils来操作数据库

* mongo-starter

    * 内建MongodbUtils来操作mongodb

* mq-starter

    * 待补充...

* nacos-starter

    * 提供nacos的配置支持

* redis-starter

    * 提供TlrobotRedisClient操作redis

# 待补充

如果你有兴趣,可以参与补充一些starters,目前我们构建方式[采取springboot的标准构建starter的方式](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration.custom-starter)

* es-starter

* kafka-starter

* sentinel-starter

* seata-starter

  ......
