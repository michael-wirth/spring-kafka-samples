package demo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;

@EnableBinding(Source.class)
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);


    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final String[] DATA = new String[]{
            "Alfie", "Beatrice", "Charlie", "Dillon", "Emily", "Freddie",
            "Grace", "Harry", "Isabella", "Jack", "Lily", "Mia",
            "Noah", "Oscar", "Sophia", "Zoe"
    };

    @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedRate = "1000"))
    public Message<?> generate() {
        String value = DATA[RANDOM.nextInt(DATA.length)];
        logger.info("Sending: " + value);
        return MessageBuilder.withPayload(value)
                .setHeader("partitionKey", value)
                .build();
    }
}
