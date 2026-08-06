package microservice.board.common.event;

import lombok.Getter;
import microservice.board.common.dataserializer.DataSerializer;

@Getter
public class Event<T extends EventPayload> {

    private Long eventId;
    private EventType type;
    private EventPayload payload;

    public static Event<EventPayload> of(Long eventId, EventType type, EventPayload payload) {
        Event<EventPayload> event = new Event<>();
        event.eventId = eventId;
        event.type = type;
        event.payload = payload;
        return event;
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static Event<EventPayload> fromJson(String json) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if (eventRaw == null) {
            return null;
        }

        Long eventId = eventRaw.getEventId();
        EventType type = EventType.from(eventRaw.getType());
        EventPayload payload = DataSerializer.deserialize(eventRaw.getPayload(), type.getPayloadClass());
        return of(eventId, type, payload);
    }

    @Getter
    private static class EventRaw {
        private Long eventId;
        private String type;
        private Object payload;
    }
}
