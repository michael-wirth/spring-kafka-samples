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
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static java.lang.String.format;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION_ID;

@Component
public class EventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    @Bean
    public Consumer<Message<CreateEvent>> handleCreateEvent() {
        return (message) ->
                LOGGER.info(format("Event received from partition %-2d : %s",
                        message.getHeaders().get(RECEIVED_PARTITION_ID, Integer.class),
                        message.getPayload()));
    }

    @Bean
    public Consumer<Message<ModifyEvent>> handleModifyEvent() {
        return (message) ->
                LOGGER.info(format("Event received from partition %-2d : %s",
                        message.getHeaders().get(RECEIVED_PARTITION_ID, Integer.class),
                        message.getPayload()));
    }

    @Bean
    public Consumer<Message<DeleteEvent>> handleDeleteEvent() {
        return (message) ->
                LOGGER.info(format("Event received from partition %-2d : %s",
                        message.getHeaders().get(RECEIVED_PARTITION_ID, Integer.class),
                        message.getPayload()));
    }
}
