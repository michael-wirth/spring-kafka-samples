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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION_ID;

@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Bean
    public Consumer<Message<String>> handleMessage() {
        return message -> {
            if (message.getPayload().equals("Zoe")) {
                throw new IllegalArgumentException(message.getPayload());
            }

            LOGGER.info(String.format("%-10s received from partition %-2d : %-100d",
                    message.getPayload(),
                    message.getHeaders().get(RECEIVED_PARTITION_ID, Integer.class),
                    message.getHeaders().get(OFFSET, Long.class)));
        };
    }

    @Bean
    public Consumer<Message<String>> handleError() {
        return message ->
                LOGGER.error(String.format("Processing of %s failed.\n\tcause: %s\n\tstacktrace: %s", message.getPayload(),
                        new String(message.getHeaders().get("x-exception-message", byte[].class)),
                        new String(message.getHeaders().get("x-exception-stacktrace", byte[].class))));
    }
}
