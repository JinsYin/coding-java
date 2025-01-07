# EDC Health Endpoint

此示例通过自定义实现 Extension 来增加一个 HTTP GET 接口到连接器。

## 要求

* Java 17（开启 `-Pjava17`）
* 工作目录为项目根目录

## 开发调试

```bash
# 先执行编译
$ mvn clean compile -pl edc/connector/health-endpoint -Pjava17

# 运行 BaseRuntime 主程序，如何运行正常，日志中会显示 Initialized、Prepared 和 Started `Health Service`（扩展名）
$ mvn exec:java -pl edc/connector/health-endpoint -Pjava17
```

## 构建运行

### 构建

```bash
$ mvn clean package -pl edc/connector/health-endpoint -amd -Pjava17
```

### 运行

```bash
# 默认端口为 8181
$ java -jar edc/connector/basic-connector/target/health-endpoint.jar
```

### 验证

```bash
$ curl http://localhost:8181/api/health
```

## 参考

* [Write your first extension][basic-02-health-endpoint]

[basic-02-health-endpoint]: https://github.com/eclipse-edc/Samples/tree/main/basic/basic-02-health-endpoint
