package demo.producer.events;

import java.time.Instant;
import java.util.UUID;

public class CreateEvent {

    private final Instant timestamp;
    private final UUID uuid;

    private final String id;
    private final String name;

    public CreateEvent(String id, String name) {
        this.uuid = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.id = id;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CreateEvent{" +
                "timestamp=" + timestamp +
                ", uuid=" + uuid +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
