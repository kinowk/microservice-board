package microservice.board.hotarticle.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.board.common.event.Event;
import microservice.board.common.event.EventPayload;
import microservice.board.common.event.EventType;
import microservice.board.hotarticle.service.HotArticleService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotArticleEventConsumer {

    private final HotArticleService hotArticleService;

    @KafkaListener(topics = {
            EventType.Topic.MICROSERVICE_BOARD_ARTICLE,
            EventType.Topic.MICROSERVICE_BOARD_COMMENT,
            EventType.Topic.MICROSERVICE_BOARD_LIKE,
            EventType.Topic.MICROSERVICE_BOARD_VIEW
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[HotArticleEventConsumer.listen] received message: {}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            hotArticleService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
