package microservice.board.view.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.event.EventType;
import microservice.board.common.event.payload.ArticleViewedEventPayload;
import microservice.board.common.outboxmessagerelay.OutboxEventPublisher;
import microservice.board.view.entity.ArticleViewCount;
import microservice.board.view.repository.ArticleViewCountBackupRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleViewCountBackupProcessor {

    private final ArticleViewCountBackupRepository articleViewCountBackupRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void backup(Long articleId, Long viewCount) {
        int result = articleViewCountBackupRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackupRepository.findById(articleId)
                    .ifPresentOrElse(
                            ignored -> {},
                            () -> articleViewCountBackupRepository.save(
                                    ArticleViewCount.of(articleId, viewCount)
                            )
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
