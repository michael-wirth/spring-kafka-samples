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
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import static demo.consumer.Consumer.MyChannels.ERROR_INPUT;
import static org.springframework.cloud.stream.messaging.Sink.INPUT;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION_ID;

@EnableBinding({Sink.class, Consumer.MyChannels.class})
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @StreamListener(INPUT)
    public void listen(@Payload String in,
                       @Header(RECEIVED_PARTITION_ID) int partition,
                       @Header(OFFSET) int offset) {
        if (in.equals("Zoe")) {
            throw new IllegalArgumentException(in);
        }

        logger.info(String.format("%-10s received from partition %-2d : %-100d", in, partition, offset));
    }

    @StreamListener(ERROR_INPUT)
    public void listenOnErrors(@Payload String in,
                               @Header("x-exception-message") byte[] exceptionMessage,
                               @Header("x-exception-stacktrace") byte[] stacktrace) {
        logger.error(String.format("Processing of %s failed.\n\tcause: %s\n\tstacktrace: %s", in, new String(exceptionMessage), new String(stacktrace)));
    }

    public interface MyChannels {
        String ERROR_INPUT = "input-error";

        @Input(ERROR_INPUT)
        SubscribableChannel errorInput();
    }
}
