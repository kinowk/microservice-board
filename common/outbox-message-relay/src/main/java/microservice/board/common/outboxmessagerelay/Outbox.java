package microservice.board.common.outboxmessagerelay;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import microservice.board.common.event.EventType;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {

    @Id
    @Column(name = "outbox_id", nullable = false, updatable = false)
    private Long outboxId;

    @Column(name = "shard_key", nullable = false)
    private Long shardKey;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static Outbox of(Long outboxId, Long shardKey, EventType eventType, String payload) {
        Outbox outbox = new Outbox();
        outbox.outboxId = outboxId;
        outbox.shardKey = shardKey;
        outbox.eventType = eventType;
        outbox.payload = payload;
        outbox.createdAt = LocalDateTime.now();
        return outbox;
    }
}
