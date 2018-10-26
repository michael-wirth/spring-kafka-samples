/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.consumer;

import demo.consumer.events.CreateEvent;
import demo.consumer.events.DeleteEvent;
import demo.consumer.events.ModifyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(Sink.class)
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @StreamListener(value = Sink.INPUT, condition = "headers['type']=='CreateEvent'")
    public void handleCreateEvent(@Payload CreateEvent in,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("Event received from partition %-2d : %s", partition, in));
    }

    @StreamListener(value = Sink.INPUT, condition = "headers['type']=='ModifyEvent'")
    public void handleModifyEvent(@Payload ModifyEvent in,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("Event received from partition %-2d : %s", partition, in));
    }

    @StreamListener(value = Sink.INPUT, condition = "headers['type']=='DeleteEvent'")
    public void handleDeleteEvent(@Payload DeleteEvent in,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("Event received from partition %-2d : %s", partition, in));
    }
}
