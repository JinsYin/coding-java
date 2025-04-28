# config-connector

基于文件配置设置 EDC 扩展。该连接器使用 `FsConfigurationExtension` 扩展来加载文件配置。

## 开发调试

```bash
# 必须先编译
$ mvn clean compile -Pjava17 -pl edc/connector/config-connector

# 运行
$ mvn exec:java -Pjava17 -pl edc/connector/config-connector \
  -Dedc.fs.config=edc/connector/config-connector/config.properties

# 验证请求（默认端口是 8181，配置文件改为了 9191），并检查运行日志（以 HEALTH 前缀开头）
$ curl http://localhost:9191/api/health
```

### 构建

```bash
$ mvn clean package -pl edc/connector/config-connector -amd -Pjava17
```

### 运行

```bash
# 配置文件的 Web HTTP 端口为 9191
$ java -jar edc/connector/config-connector/target/config-connector.jar \
  -Dedc.fs.config=edc/connector/config-connector/config.properties
```

### 验证

```bash
$ curl http://localhost:9191/api/health
```

## 参考

* [`FsConfigurationExtension.java`](https://github.com/eclipse-edc/Connector/blob/releases/extensions/common/configuration/configuration-filesystem/src/main/java/org/eclipse/edc/configuration/filesystem/FsConfigurationExtension.java)