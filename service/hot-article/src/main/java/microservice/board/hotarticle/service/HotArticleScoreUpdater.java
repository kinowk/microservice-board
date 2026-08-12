package microservice.board.hotarticle.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.event.Event;
import microservice.board.common.event.EventPayload;
import microservice.board.hotarticle.repository.ArticleCreatedTimeRepository;
import microservice.board.hotarticle.repository.HotArticleListRepository;
import microservice.board.hotarticle.service.eventhandler.EventHandler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HotArticleScoreUpdater {

    private final HotArticleListRepository hotArticleListRepository;
    private final HotArticleScoreCalculator hotArticleScoreCalculator;
    private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

    private static final long HOT_ARTICLE_COUNT = 10;
    private static final Duration HOT_ARTICLE_TTL = Duration.ofDays(10);

    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {
        Long articleId = eventHandler.findArticleId(event);
        LocalDateTime createdAt = articleCreatedTimeRepository.read(articleId);

        if (!isArticleCreatedToday(createdAt)) {
            return;
        }

        eventHandler.handle(event);

        long score = hotArticleScoreCalculator.calculate(articleId);
        hotArticleListRepository.add(
                articleId,
                createdAt,
                score,
                HOT_ARTICLE_COUNT,
                HOT_ARTICLE_TTL
        );
    }

    private boolean isArticleCreatedToday(LocalDateTime createdAt) {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDate.now());
    }
}
