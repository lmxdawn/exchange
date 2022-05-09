# Seata 操作步骤

## 下载

>  https://github.com/seata/seata/releases

## 创建数据库

> 在 `server/db` 目录找到数据文件，创建一个 seata 数据库，utf8字符，导入SQL

## 上传配置文件到 nacos

> 注册中心和配置中心用的 nacos，所以先启动 nacos

> 在 `config-center/nacos` 目录里面找到运行命令，把配置文件写入 nacos，配置文件里面写入了 seata 的db配置，把里面的db链接信息替换掉，seata 就会用 nacos 获取到数据库配置

```shell
$ ./nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -u nacos -w nacos
```

## 启动
> 配置文件在 `client/conf` 目录

> `./bin/seata-server.sh -h 公网ip（和项目同网就不用指定了）`