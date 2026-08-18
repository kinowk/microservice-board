package microservice.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import microservice.board.articleread.repository.ArticleQueryModelRepository;
import microservice.board.common.event.Event;
import microservice.board.common.event.EventType;
import microservice.board.common.event.payload.ArticleDeletedEventPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {

    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        Long articleId = event.getPayload().getArticleId();
        articleQueryModelRepository.delete(articleId);
    }


@Override
public boolean supports(Event<ArticleDeletedEventPayload> event) {
    return EventType.ARTICLE_DELETED == event.getType();
}
}