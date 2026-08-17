package microservice.board.view.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.event.EventType;
import microservice.board.common.event.payload.ArticleViewedEventPayload;
import microservice.board.common.outboxmessagerelay.OutboxEventPublisher;
import microservice.board.view.entity.ArticleViewCount;
import microservice.board.view.repository.ArticleViewCountBackUpRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {

    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void backUp(Long articleId, Long viewCount) {
        int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackUpRepository.findById(articleId)
                    .ifPresentOrElse(
                            ignored -> {},
                            () -> articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount))
                    );
        }

        outboxEventPublisher.publish(
                EventType.ARTICLE_VIEWED,
                ArticleViewedEventPayload.builder()
                        .articleId(articleId)
                        .articleViewCount(viewCount)
                        .build(),
                articleId
        );
    }
}
