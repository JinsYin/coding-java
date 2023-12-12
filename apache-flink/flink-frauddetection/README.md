# Flink Fraud Detection

## 代码生成

```bash
$ mvn archetype:generate \
    -DarchetypeGroupId=org.apache.flink \
    -DarchetypeArtifactId=flink-walkthrough-datastream-java \
    -DarchetypeVersion=1.14.5 \
    -DgroupId=flink-frauddetection \
    -DartifactId=flink-frauddetection \
    -Dversion=0.1 \
    -Dpackage=spendreport \
    -DinteractiveMode=false
```

## 构建

```bash
$ mvn clean package
```

## 本地运行

```bash
# 方式一: 运行 Main Class（使用 LocalEnvironment 会启动一个本地 Web 服务，地址为 `localhost:18081`）
$ java -jar target/flink-frauddetection-0.1.jar # in batch
```

```bash
# 方式二：运行其他类（自动识别一个 LocalEnvironment 会启动一个本地 Web 服务，地址为 `localhost:18081`）
$ java -cp target/flink-frauddetection-0.1.jar com.github.flink.FraudDetectionJob # in streaming
```

## 参考

* [Fraud Detection with the DataStream API](https://nightlies.apache.org/flink/flink-docs-release-1.14/docs/try-flink/datastream/)
