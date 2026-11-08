package microservice.board.common.outboxmessagerelay;

import lombok.RequiredArgsConstructor;
import microservice.board.common.event.Event;
import microservice.board.common.event.EventPayload;
import microservice.board.common.event.EventType;
import microservice.board.common.snowflake.Snowflake;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final Snowflake outboxIdSnowflake = new Snowflake();
    private final Snowflake eventIdSnowflake = new Snowflake();
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType type, EventPayload payload, Long shardKey) {
        Outbox outbox = Outbox.of(
                eventIdSnowflake.nextId(),
                shardKey % MessageRelayConstants.SHARD_COUNT,
                type,
                Event.of(
                        eventIdSnowflake.nextId(),
                        type,
                        payload
                ).toJson()
        );
        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }
}
