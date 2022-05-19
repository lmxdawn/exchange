# 工程简介
> Java 交易所源码，前端APP使用uniapp开发，后端使用java开发，采用 Spring Cloud Alibaba 微服务架构，撮合引擎使用`Disruptor`高效内存队列，所有服务可以实现分布式部署，websocket推送通过增加一层路由服务实现分布式部署

# 前端说明

> *之前由于uniapp的闪屏一度准备放弃，暂时解决闪屏问题，重新开始开发*

# ~~由于前端暂缓，uniapp的限制，暂停开发~~

[uni-app切换页面闪屏或者白屏](https://ask.dcloud.net.cn/question/73807)

[前端代码，点击跳转](https://github.com/lmxdawn/exchange-uniapp)

# 依赖环境

> 先运行 Nacos 服务。[点击跳转官方文档](https://nacos.io/zh-cn/docs/quick-start.html)

- Linux/Unix/Mac
```shell
$ sh startup.sh -m standalone
```
- 如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：
```shell
bash startup.sh -m standalone
```
- Windows
```shell
$ startup.cmd -m standalone
```

> 再运行 Seata 服务。[点击跳转官方文档](https://seata.io/zh-cn/docs/ops/deploy-server.html)

- 在 `script/seata` 目录下面查看配置seata的教程，需要运行SQL和把配置文件传到nacos


- 在 Linux/Mac 下
```shell
$ ./bin/seata-server.sh -h 127.0.0.1 -p 8091 -m db
```
- 在 Windows 下
```shell
$ bin\seata-server.bat -h 127.0.0.1 -p 8091 -m db
```

> 再运行 rocketmq 消息队列服务。[点击跳转官方文档](https://rocketmq.apache.org/docs/quick-start)

- *rocketmq分name服务和broker服务*


- Linux
```shell
# Start Name Server 启动名称服务器
$ nohup sh bin/mqnamesrv &
# Start Broker 启动代理
$ nohup sh bin/mqbroker -n localhost:9876 &
```
- Windows
 
- 先设置环境变量：`ROCKETMQ_HOME="D:\rocketmq"`
```shell
# Start Name Server 启动名称服务器
$ .\bin\mqnamesrv.cmd
# Start Broker 启动代理
$ .\bin\mqbroker.cmd -n localhost:9876
```

> 如果windows启动代理报错：`错误: 找不到或无法加载主类 Files\Java\jdk1.8.0_301\lib\dt.jar;C:\Program，这个报错原因是因为java环境安装路径包含了空格`

*解决方案：[点击查看](https://blog.csdn.net/s724542558/article/details/119996755) runbroker.cmd 内容在文章底部有*

# 下载-打包

```shell
git clone https://github.com/lmxdawn/exchange.git

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

> Maven 3.6.1 版本控制

> RocketMQ 4.9.3 消息队列

> Nacos 2.0.3  操作文档：https://nacos.io/zh-cn/docs/quick-start.html

> Redis 3 缓存服务

> MySQL 5.7 数据库服务

> MongoDB 5.0.5 K线数据库服务

> Seata 1.3.0 分布式事务解决方案

> Disruptor 3.4.4  高效的内存队列，为了多线程操作变量时并发问题，用这个可以不用锁

> Netty 4.1.71.Final  ws消息推送

# 其它

> `Generate MyPOJOs.groovy` 生成数据库Model

# RocketMQ 的 runbroker.cmd

```shell
@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

set JAVAHOME="%JAVA_HOME%"
if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

setlocal

set BASE_DIR=%~dp0
set BASE_DIR=%BASE_DIR:~0,-1%
for %%d in (%BASE_DIR%) do set BASE_DIR=%%~dpd

set CLASSPATHSTR="%CLASSPATH%"
set CLASSPATH=.;%BASE_DIR%conf;%BASE_DIR%lib\*;%CLASSPATHSTR%

rem ===========================================================================================
rem  JVM Configuration
rem ===========================================================================================
set "JAVA_OPT=%JAVA_OPT% -server -Xms2g -Xmx2g"
set "JAVA_OPT=%JAVA_OPT% -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=30 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:SurvivorRatio=8"
set "JAVA_OPT=%JAVA_OPT% -verbose:gc -Xloggc:%USERPROFILE%\mq_gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintAdaptiveSizePolicy"
set "JAVA_OPT=%JAVA_OPT% -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30m"
set "JAVA_OPT=%JAVA_OPT% -XX:-OmitStackTraceInFastThrow"
set "JAVA_OPT=%JAVA_OPT% -XX:+AlwaysPreTouch"
set "JAVA_OPT=%JAVA_OPT% -XX:MaxDirectMemorySize=15g"
set "JAVA_OPT=%JAVA_OPT% -XX:-UseLargePages -XX:-UseBiasedLocking"
set "JAVA_OPT=%JAVA_OPT% -Djava.ext.dirs=%BASE_DIR%lib;%JAVAHOME%\jre\lib\ext"
set "JAVA_OPT=%JAVA_OPT% -cp %CLASSPATH%"

"%JAVA%" %JAVA_OPT% %*
```