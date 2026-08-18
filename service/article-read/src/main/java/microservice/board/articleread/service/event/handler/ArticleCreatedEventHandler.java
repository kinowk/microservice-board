package microservice.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import microservice.board.articleread.repository.ArticleQueryModel;
import microservice.board.articleread.repository.ArticleQueryModelRepository;
import microservice.board.common.event.Event;
import microservice.board.common.event.EventType;
import microservice.board.common.event.payload.ArticleCreatedEventPayload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {

    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleCreatedEventPayload> event) {
        articleQueryModelRepository.create(
                ArticleQueryModel.create(event.getPayload()),
                Duration.ofDays(1)
        );
    }

    @Override
    public boolean supports(Event<ArticleCreatedEventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }
}
