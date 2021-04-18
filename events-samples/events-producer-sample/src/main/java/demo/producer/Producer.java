package demo.producer;

import demo.producer.events.CreateEvent;
import demo.producer.events.DeleteEvent;
import demo.producer.events.ModifyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.Random;

@EnableBinding(Source.class)
public class Producer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private static final String[] DATA = {
            "Alfie", "Beatrice", "Charlie", "Dillon", "Emily", "Freddie",
            "Grace", "Harry", "Isabella", "Jack", "Lily", "Mia",
            "Noah", "Oscar", "Sophia", "Zoe"
    };

    private final Random random = new Random();

    private final MessageChannel output;

    public Producer(MessageChannel output) {
        this.output = output;
    }

    @Override
    public void run(String... args) throws Exception {
        // send Create events
        Arrays.stream(DATA)
                .map(it -> new CreateEvent(it.toUpperCase(), it))
                .forEach(this::send);

        // send Modify events
        Arrays.stream(DATA)
                .map(it -> new ModifyEvent(it.toUpperCase(), random.nextInt(), random.nextBoolean()))
                .forEach(this::send);

        // send Delete events
        Arrays.stream(DATA)
                .map(it -> new DeleteEvent(it.toUpperCase(), "any reason"))
                .forEach(this::send);
    }

    public void send(Object event) {
        LOGGER.info("Sending: " + event);

        output.send(MessageBuilder
                .withPayload(event)
                .setHeaderIfAbsent("type", event.getClass().getSimpleName())
                .build());
    }

}
