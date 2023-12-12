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

package com.github.flink;

import org.apache.flink.api.common.RuntimeExecutionMode;
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

/**
 * Flink Session Task
 */
public class FlinkJarTask {

	public static void main(String[] args) throws Exception {
		executeExample();
	}

	public static void executeExample() throws Exception {
		Configuration config = new Configuration();
		config.setInteger(RestOptions.PORT.key(), 18081); // default: 8081
		config.setInteger(RestOptions.BIND_PORT.key(), 18081); // default: 8081
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(config);
		env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

		DataGenerator<Integer> dataGenerator = SequenceGenerator.intGenerator(0, 10000); // batch task
		DataGenerator<Integer> randGenerator = RandomGenerator.intGenerator(0, 1000); // continuous task
		DataGeneratorSource<Integer> dataGeneratorSource = new DataGeneratorSource<>(dataGenerator);

		DataStream<Integer> dataGen = env
				.addSource(dataGeneratorSource, TypeInformation.of(Integer.class))
				.name("data-gen");
		dataGen.addSink(new PrintSinkFunction<>())
				.name("send-to-console");

		executeByEnvironment(env, "Flink Session Task");
	}

	// --------------------------------------------------

	public static void executeByEnvironment(StreamExecutionEnvironment env, String jobName) throws Exception {
		env.executeAsync(jobName);
	}
}
