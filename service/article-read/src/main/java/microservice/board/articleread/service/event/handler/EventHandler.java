package microservice.board.articleread.service.event.handler;

import microservice.board.common.event.Event;
import microservice.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);
    boolean supports(Event<T> event);
}
