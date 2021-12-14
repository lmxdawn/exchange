# 工程简介
> 交易所项目

# 下载-打包

```shell
git clone https://github.com/lmxdawn/im-java.git

# 整体打包
mvn -Dmaven.test.skip=true clean package
# 单独打包某个模块，这里的 user 就是模块名称
mvn -Dmaven.test.skip=true -pl user -am clean package

# 运行
nohup java -jar -Dspring.profiles.active=prod admin/target/admin-0.0.1-SNAPSHOT.jar &
nohup java -jar -Dspring.profiles.active=prod gateway/target/gateway-0.0.1-SNAPSHOT.jar &
nohup java -jar -Dspring.profiles.active=prod user/target/user-0.0.1-SNAPSHOT.jar &

```

# 模块说明

> dubbo-api：dubbo的API

> gateway：网关 9001

> admin：后台 9002

> other：其它模块 9003

> user：用户 9004

> wallet：钱包 9005

> market：行情 9006

> trade：交易 9007

> match：撮合 9008

> ws：消息推送 9009

> ws-route：消息推送路由 9010

# Swagger

> gateway的方式访问swagger，gateway启动后访问： `http://ip:prot/swagger-ui/index.html`


# 使用

> dubbo 做微服务的调用

> Seata 做微服务的分布式事务，操作目录 `script/seata` [安装步骤](https://segmentfault.com/a/1190000039403317)

> 使用 jwt 做dubbo服务间的鉴权

> example 目录是示例文件，需要加 module 时创建一个module，然后复制example里面的代码，全局替换 `example` 

# 依赖

> Java 8

> Maven 3.6.1

> Nacos 2.0.3  操作文档：https://nacos.io/zh-cn/docs/quick-start.html

> Redis 3

> MySQL 5.7

> Seata 1.3.0

> Disruptor 3.4.4  高效的内存队列，为了多线程操作变量时并发问题，用这个可以不用锁

> Netty 4.1.71.Final  ws消息推送

# 其它

> `Generate MyPOJOs.groovy` 生成数据库Model

