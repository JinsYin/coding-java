# Apache Thrift

[笔记](https://www.yuque.com/data.com/emr/thrift)

## 编译 Thrift

前提：安装 thrift 编译器。

* 方式一

```bash
cd src/main/resources

# 代码生成路径为 src/main/resources/gen-java.com.github.coding.thrift
thrift --gen java RpcService.thrift
```

* 方式二

```bash
# 代码生成路径为 target/generated-sources/thrift
mvn thrift:compile
```

## 实践

1. 启动 ThriftServer
2. 启动 ThriftClient

## 参考

* [github.com/zavier/thrift-demo](https://github.com/zavier/thrift-demo)