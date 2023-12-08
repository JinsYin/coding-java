/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spendreport;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.source.datagen.DataGenerator;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.api.functions.source.datagen.RandomGenerator;
import org.apache.flink.streaming.api.functions.source.datagen.SequenceGenerator;
import org.apache.flink.streaming.api.graph.StreamGraph;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.source.TransactionSource;

/**
 * Flink Session Task
 */
public class FlinkSessionTask {

	public static void main(String[] args) throws Exception {
		executeExample2();
	}

	public static void executeExample1() throws Exception {
		Configuration config = new Configuration();
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(config);

		DataGenerator<Integer> dataGenerator = SequenceGenerator.intGenerator(0, 1000000);
		DataGenerator<Integer> randGenerator = RandomGenerator.intGenerator(0, 1000);
		DataGeneratorSource<Integer> dataGeneratorSource = new DataGeneratorSource<>(randGenerator);

		DataStream<Integer> dataGen = env
				.addSource(dataGeneratorSource, TypeInformation.of(Integer.class))
				.name("data-gen");
		dataGen.addSink(new PrintSinkFunction<>())
				.name("send-to-console");

		executeByEnvironment(env, "Flink Session Task");
	}

	public static void executeExample2() throws Exception {
		Configuration config = new Configuration();
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(config);

		DataStream<Transaction> transactions = env
				.addSource(new TransactionSource())
				.name("transactions");
		DataStream<Alert> alerts = transactions
				.keyBy(Transaction::getAccountId)
				.process(new FraudDetector())
				.name("fraud-detector")
				;
		alerts.addSink(new PrintSinkFunction<>())
				.name("send-alerts");

		StreamGraph streamGraph = env.getStreamGraph();
		System.out.println(streamGraph.getStreamingPlanAsJSON());
		System.out.println("-------------------------");
		System.out.println(streamGraph.getJobGraph());

		executeByStreamGraph(streamGraph);
	}

	// --------------------------------------------------

	public static void executeByEnvironment(StreamExecutionEnvironment env, String jobName) throws Exception {
		env.executeAsync(jobName);
	}

	public static void executeByStreamGraph(StreamGraph streamGraph) throws Exception {
		Configuration config = new Configuration();
		// 要求引入 `org.apache.flink:flink-runtime-web` 包
		config.setInteger(RestOptions.PORT.key(), 18081); // default: 8081
		config.setInteger(RestOptions.BIND_PORT.key(), 18081); // default: 8081
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(config);
		// env.getExecutionPlan() / env.getStreamGraph(false).getStreamingPlanAsJSON() / streamGraph.getStreamingPlanAsJSON()
		env.executeAsync(streamGraph);
	}

	public static void executeByStreamGraph(StreamGraph streamGraph, Configuration config) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(config);
		env.executeAsync(streamGraph);
	}
}
