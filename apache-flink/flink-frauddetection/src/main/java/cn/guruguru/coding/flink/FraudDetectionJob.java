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

package cn.guruguru.coding.flink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.source.TransactionSource;

/**
 * Skeleton code for the datastream walkthrough
 */
public class FraudDetectionJob {
	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();
		config.setInteger(RestOptions.PORT.key(), 18081); // default: 8081
		config.setInteger(RestOptions.BIND_PORT.key(), 18081); // default: 8081
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(config); // 如果使用 `java -jar` 运行会自动检测为 Local Environment

		DataStream<Transaction> transactions = env
			.addSource(new TransactionSource())
			.name("transactions");

		DataStream<Alert> alerts = transactions
			.keyBy(Transaction::getAccountId)
			.process(new FraudDetector())
			.name("fraud-detector");

		alerts
			.addSink(new AlertSink())
			.name("send-alerts");

		env.execute("Fraud Detection");
	}

	/**
	 * Skeleton code for implementing a fraud detector.
	 */
	public static class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {

		private static final long serialVersionUID = 1L;

		private static final double SMALL_AMOUNT = 1.00;
		private static final double LARGE_AMOUNT = 500.00;
		private static final long ONE_MINUTE = 60 * 1000;

		@Override
		public void processElement(
				Transaction transaction,
				Context context,
				Collector<Alert> collector) throws Exception {

			Alert alert = new Alert();
			alert.setId(transaction.getAccountId());

			collector.collect(alert);
		}
	}
}
