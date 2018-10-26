package demo.producer.events;

import java.time.Instant;
import java.util.UUID;

public class DeleteEvent {

    private final Instant timestamp;
    private final UUID uuid;

    private final String id;
    private final String reason;

    public DeleteEvent(String id, String reason) {
        this.uuid = UUID.randomUUID();
        this.timestamp = Instant.now();

        this.id = id;
        this.reason = reason;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "DeleteEvent{" +
                "timestamp=" + timestamp +
                ", uuid=" + uuid +
                ", id='" + id + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
