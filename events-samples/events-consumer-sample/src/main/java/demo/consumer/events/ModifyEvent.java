package demo.consumer.events;

import java.time.Instant;
import java.util.UUID;

public class ModifyEvent {

    private final Instant timestamp;
    private final UUID uuid;

    private final String id;
    private final int anInt;
    private final boolean aBoolean;

    public ModifyEvent(String id, int anInt, boolean aBoolean) {
        this.uuid = UUID.randomUUID();
        this.timestamp = Instant.now();

        this.id = id;
        this.anInt = anInt;
        this.aBoolean = aBoolean;
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

    public int getAnInt() {
        return anInt;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    @Override
    public String toString() {
        return "ModifyEvent{" +
                "timestamp=" + timestamp +
                ", uuid=" + uuid +
                ", id='" + id + '\'' +
                ", anInt=" + anInt +
                ", aBoolean=" + aBoolean +
                '}';
    }
}
