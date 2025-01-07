# basic-connector

此示例构建和运行一个简单的连接器。

## 要求

* Java 17（开启 `-Pjava17`）
* 工作目录为项目根目录

## 开发调试

```bash
# 运行 BaseRuntime 主程序
$ mvn exec:java -pl edc/connector/basic-connector -Pjava17
```

## 构建运行

### 构建

```bash
$ mvn clean package -pl edc/connector/basic-connector -amd -Pjava17
```

### 运行

```bash
# 默认端口为 8181
$ java -jar edc/connector/basic-connector/target/basic-connector.jar
```

## 参考

* [Running a simple connector][basic-01-basic-connector]

[basic-01-basic-connector]: https://github.com/eclipse-edc/Samples/tree/main/basic/basic-01-basic-connector
