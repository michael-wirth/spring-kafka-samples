package demo.producer;

import demo.producer.events.CreateEvent;
import demo.producer.events.DeleteEvent;
import demo.producer.events.ModifyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@Component
public class Producer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private static final String[] DATA = {
            "Alfie", "Beatrice", "Charlie", "Dillon", "Emily", "Freddie",
            "Grace", "Harry", "Isabella", "Jack", "Lily", "Mia",
            "Noah", "Oscar", "Sophia", "Zoe"
    };

    private final Random random = new Random();

    private final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void run(String... args) throws Exception {
        // send Create events
        Arrays.stream(DATA)
                .map(it -> new CreateEvent(it.toUpperCase(), it))
                .forEach(sendQueue::add);

        // send Modify events
        Arrays.stream(DATA)
                .map(it -> new ModifyEvent(it.toUpperCase(), random.nextInt(), random.nextBoolean()))
                .forEach(sendQueue::add);

        // send Delete events
        Arrays.stream(DATA)
                .map(it -> new DeleteEvent(it.toUpperCase(), "any reason"))
                .forEach(sendQueue::add);
    }

    @Bean
    public Supplier<Message<Object>> eventSupplier() {

        return () -> {
            Object event = sendQueue.poll();
            if (event == null) {
                return null;
            }
            LOGGER.info("Sending: " + event);
            return MessageBuilder.withPayload(event)
                    .setHeaderIfAbsent("type", event.getClass().getSimpleName())
                    .build();
        };
    }

}
