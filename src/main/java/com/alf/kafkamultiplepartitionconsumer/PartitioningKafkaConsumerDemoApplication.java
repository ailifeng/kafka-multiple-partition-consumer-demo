/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alf.kafkamultiplepartitionconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.*;

@SpringBootApplication
@EnableBinding({C5StreamChannel0.class, C5StreamChannel1.class, C6Stream.class})
public class PartitioningKafkaConsumerDemoApplication {
    private static final Set<String> keyPartitionSet = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(PartitioningKafkaConsumerDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PartitioningKafkaConsumerDemoApplication.class, args);
    }

    @StreamListener(C5StreamChannel0.INPUT)
    public void listenC5Channel0(@Payload String in,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header("partitionKey") String partitionKey) {
        synchronized (this) {
            String keyPartitionPair = partitionKey + "-" + partition;
            keyPartitionSet.add(keyPartitionPair);
        }
        logger.info("Thread[{}], message: {}, partition id: {}, partition key: {}",
                Thread.currentThread().getName(), in, partition, partitionKey);
        printSet(keyPartitionSet);
    }

    @StreamListener(C5StreamChannel1.INPUT)
    public void listenC5Channel1(@Payload String in,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header("partitionKey") String partitionKey) {
        logger.info("Thread[{}], message: {}, partition id: {}, partition key: {}",
                Thread.currentThread().getName(), in, partition, partitionKey);
    }

    @StreamListener(C6Stream.INPUT)
    public void listenC6Channel(@Payload String in,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                 @Header("partitionKey") String partitionKey) {
        logger.info("Thread[{}], message: {}, partition id: {}, partition key: {}",
                Thread.currentThread().getName(), in, partition, partitionKey);
    }

    private void printSet(Set<String> set) {
        Iterator<String> it = set.iterator();
        logger.info("set");
        while (it.hasNext()) {
            logger.info(it.next());
        }
    }
}
