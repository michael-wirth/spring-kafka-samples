package demo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;

@Component
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final String[] DATA = new String[]{
            "Alfie", "Beatrice", "Charlie", "Dillon", "Emily", "Freddie",
            "Grace", "Harry", "Isabella", "Jack", "Lily", "Mia",
            "Noah", "Oscar", "Sophia", "Zoe"
    };

    @Bean
    public Supplier<Message<String>> sendMessage() {
        return () -> {
            String value = DATA[RANDOM.nextInt(DATA.length)];
            LOGGER.info("Sending: " + value);
            return MessageBuilder.withPayload(value)
                    .setHeader("partitionKey", value)
                    .build();
        };
    }
}
