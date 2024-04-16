//package com.github.flink;
//
//import org.apache.flink.api.common.eventtime.Watermark;
//import org.apache.flink.api.common.functions.FilterFunction;
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.calcite.shaded.com.fasterxml.jackson.databind.JsonNode;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.sink.SinkFunction;
//import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
//import org.apache.flink.streaming.api.operators.KeyedProcessOperator;
//import org.apache.flink.table.api.DataTypes;
//import org.apache.flink.table.api.EnvironmentSettings;
//import org.apache.flink.table.api.Schema;
//import org.apache.flink.table.api.Table;
//import org.apache.flink.table.api.TableDescriptor;
//import org.apache.flink.table.api.TableEnvironment;
//import org.apache.flink.table.api.TableResult;
//import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
//import org.apache.flink.types.Row;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.apache.flink.table.api.Expressions.$;
//
//public class FlinkTableApi {
//
//    public static void main(String[] args) throws Exception {
//        dataStream();
//    }
//
//    public static void table() {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamTableEnvironment streamTableEnv = StreamTableEnvironment.create(env);
//
//        // Table Source
//        // Table table = streamTableEnv.fromValues(Arrays.asList(1, 2, null)).as("age");
//        Table table = streamTableEnv.fromValues().as("age");
//
//        streamTableEnv.toDataStream(table).map(new MapFunction<Row, Object>() {
//            @Override
//            public Object map(Row value) throws Exception {
//                return null;
//            }
//        }).name("transformation").setParallelism(3).transform("trans", null, null).addSink(new SinkFunction<Object>() {
//            @Override
//            public void invoke(Object value, Context context) throws Exception {
//                SinkFunction.super.invoke(value, context);
//            }
//
//            @Override
//            public void writeWatermark(Watermark watermark) throws Exception {
//                SinkFunction.super.writeWatermark(watermark);
//            }
//
//            @Override
//            public void finish() throws Exception {
//                SinkFunction.super.finish();
//            }
//        }).name("target").setParallelism(2);
//    }
//
//    public static void dataStream() throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamTableEnvironment streamTableEnv = StreamTableEnvironment.create(env);
//
//        // Table Source
//        // Table table = streamTableEnv.fromValues(Arrays.asList(1, 2, null)).as("age");
//        Table table = streamTableEnv.fromValues().as("age");
//
//        env.addSource(new RichSourceFunction<JsonNode>() {
//            @Override
//            public void run(SourceContext<JsonNode> ctx) throws Exception {
//                ctx.collect(null);
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//        }).name("database-source").map(new MapFunction<JsonNode, Object>() {
//            @Override
//            public Object map(JsonNode value) throws Exception {
//                return null;
//            }
//        }).name("expression-transformation").filter(new FilterFunction<Object>() {
//            @Override
//            public boolean filter(Object value) throws Exception {
//                return false;
//            }
//        }).name("filter-transformation").addSink(new SinkFunction<Object>() {
//            @Override
//            public void invoke(Object value, Context context) throws Exception {
//                SinkFunction.super.invoke(value, context);
//            }
//
//            @Override
//            public void writeWatermark(Watermark watermark) throws Exception {
//                SinkFunction.super.writeWatermark(watermark);
//            }
//
//            @Override
//            public void finish() throws Exception {
//                SinkFunction.super.finish();
//            }
//        }).name("database-target");
//
//        env.execute("Flink Session Task");
//    }
//
//    public static void testTableApi() {
//        Configuration config = new Configuration();
//        EnvironmentSettings settings = EnvironmentSettings.newInstance()
//                .inStreamingMode()
//                .build();
//        TableEnvironment tableEnv = TableEnvironment.create(settings);
//
//        StreamTableEnvironment streamTableEnv = StreamTableEnvironment
//                .create(new StreamExecutionEnvironment());
//
//        Schema schema = Schema.newBuilder().fromFields(
//                Collections.singletonList("age"),
//                Collections.singletonList(DataTypes.INT())
//        ).build();
//
//        // Source
//        Table table = tableEnv.fromValues(Arrays.asList(1, 2, null)).as("age");
//        // interpret the insert-only Table as a DataStream again
//        DataStream<Row> resultStream = streamTableEnv.toDataStream(table);
//
//        // Transformation / Expression
//        Table filteredTable = table.filter(
//                $("age").isNotNull()
//        );
//
//        filteredTable.printSchema();
//
//        // register a table
//        TableDescriptor descriptor = TableDescriptor.forConnector("print").schema(schema).build();
//        tableEnv.createTable("default_catalog.default_database.t1", descriptor);
//
//        // TableResult result = filteredTable.execute();
//        TableResult result = filteredTable.executeInsert("t1");
//
//        System.out.println("------------");
//        System.out.println(String.join(",", tableEnv.listTables()));
//        result.print();
//        System.out.println(result);
//        System.out.println(result.collect());
//        System.out.println(result.getResolvedSchema());
//        System.out.println(result.getResultKind());
//        System.out.println(result.getJobClient().get());
//    }
//}
