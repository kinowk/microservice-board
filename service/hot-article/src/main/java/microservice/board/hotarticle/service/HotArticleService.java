package microservice.board.hotarticle.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.event.Event;
import microservice.board.common.event.EventPayload;
import microservice.board.common.event.EventType;
import microservice.board.hotarticle.client.ArticleClient;
import microservice.board.hotarticle.repository.HotArticleListRepository;
import microservice.board.hotarticle.service.eventhandler.EventHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HotArticleService {

    private final ArticleClient articleClient;
    private final List<EventHandler> eventHandlers;
    private final HotArticleScoreUpdater hotArticleScoreUpdater;
    private final HotArticleListRepository hotArticleListRepository;

    public void handleEvent(Event<EventPayload> event) {
        EventHandler<EventPayload> eventHandler = findEventHandler(event);
        if (eventHandler == null) {
            return;
        }

        if (isArticleCreatedOrDeleted(event)) {
            eventHandler.handle(event);
        } else {
            hotArticleScoreUpdater.update(event, eventHandler);
        }
    }

    public HotArticleResult.Read readAll(String date) {
        List<ArticleClient.ArticleResponse.Read> responses = hotArticleListRepository.readAll(date).stream()
                .map(articleClient::read)
                .filter(Objects::nonNull)
                .toList();

        return HotArticleResult.Read.from(responses);
    }

    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                .orElse(null);
    }

    private boolean isArticleCreatedOrDeleted(Event<EventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType() || EventType.ARTICLE_DELETED == event.getType();
    }
}
