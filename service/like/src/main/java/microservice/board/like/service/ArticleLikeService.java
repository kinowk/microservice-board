package microservice.board.like.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.event.EventType;
import microservice.board.common.event.payload.ArticleLikedEventPayload;
import microservice.board.common.event.payload.ArticleUnlikedEventPayload;
import microservice.board.common.outboxmessagerelay.OutboxEventPublisher;
import microservice.board.common.snowflake.Snowflake;
import microservice.board.like.entity.ArticleLike;
import microservice.board.like.entity.ArticleLikeCount;
import microservice.board.like.repository.ArticleLikeCountRepository;
import microservice.board.like.repository.ArticleLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleLikeService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleLikeCountRepository articleLikeCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    public ArticleLikeResult.Read read(ArticleLikeCommand.Read command) {
        return articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .map(ArticleLikeResult.Read::from)
                .orElseThrow();
    }

    public long count(Long articleId) {
        return articleLikeCountRepository.findById(articleId)
                .map(ArticleLikeCount::getLikeCount)
                .orElseThrow();
    }

    /**
     * atomic update
     *
     * @param command
     */
    @Transactional
    public void likePessimisticLock1(ArticleLikeCommand.Like command) {
        ArticleLike articleLike = articleLikeRepository.save(
                ArticleLike.of(
                        snowflake.nextId(),
                        command.articleId(),
                        command.userId()
                )
        );

        int result = articleLikeCountRepository.increase(command.articleId());
        if (result == 0) {
            articleLikeCountRepository.save(
                    ArticleLikeCount.of(command.articleId(), 1L)
            );
        }

        outboxEventPublisher.publish(
                EventType.ARTICLE_LIKED,
                ArticleLikedEventPayload.builder()
                        .articleLikeId(articleLike.getArticleLikeId())
                        .articleId(articleLike.getArticleLikeId())
                        .userId(articleLike.getUserId())
                        .createdAt(articleLike.getCreatedAt())
                        .articleLikeCount(count(articleLike.getArticleId()))
                        .build(),
                articleLike.getArticleId()
        );
    }

    /**
     * atomic update
     *
     * @param command
     */
    @Transactional
    public void unlikePessimisticLock1(ArticleLikeCommand.Unlike command) {
        articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    articleLikeCountRepository.decrease(command.articleId());
                    outboxEventPublisher.publish(
                            EventType.ARTICLE_UNLIKED,
                            ArticleUnlikedEventPayload.builder()
                                    .articleLikeId(articleLike.getArticleLikeId())
                                    .articleId(articleLike.getArticleId())
                                    .userId(articleLike.getUserId())
                                    .createdAt(articleLike.getCreatedAt())
                                    .articleLikeCount(count(articleLike.getArticleId()))
                                    .build(),
                            articleLike.getArticleId()
                    );
                });
    }

    /**
     * select for update
     *
     * @param command
     */
    @Transactional
    public void likePessimisticLock2(ArticleLikeCommand.Like command) {
        ArticleLike articleLike = articleLikeRepository.save(
                ArticleLike.of(
                        snowflake.nextId(),
                        command.articleId(),
                        command.userId()
                )
        );

        ArticleLikeCount articleLikeCount = articleLikeCountRepository.findLockedByArticleId(command.articleId())
                .orElseGet(() -> ArticleLikeCount.of(command.articleId(), 0L));
        articleLikeCount.increase();
        articleLikeCountRepository.save(articleLikeCount);

        outboxEventPublisher.publish(
                EventType.ARTICLE_LIKED,
                ArticleLikedEventPayload.builder()
                        .articleLikeId(articleLike.getArticleLikeId())
                        .articleId(articleLike.getArticleLikeId())
                        .userId(articleLike.getUserId())
                        .createdAt(articleLike.getCreatedAt())
                        .articleLikeCount(count(articleLike.getArticleId()))
                        .build(),
                articleLike.getArticleId()
        );
    }

    /**
     * select for update
     *
     * @param command
     */
    @Transactional
    public void unlikePessimisticLock2(ArticleLikeCommand.Unlike command) {
        articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    ArticleLikeCount articleLikeCount = articleLikeCountRepository.findLockedByArticleId(command.articleId()).orElseThrow();
                    articleLikeCount.decrease();
                    outboxEventPublisher.publish(
                            EventType.ARTICLE_UNLIKED,
                            ArticleUnlikedEventPayload.builder()
                                    .articleLikeId(articleLike.getArticleLikeId())
                                    .articleId(articleLike.getArticleId())
                                    .userId(articleLike.getUserId())
                                    .createdAt(articleLike.getCreatedAt())
                                    .articleLikeCount(count(articleLike.getArticleId()))
                                    .build(),
                            articleLike.getArticleId()
                    );
                });
    }

    @Transactional
    public void likeOptimisticLock(ArticleLikeCommand.Like command) {
        ArticleLike articleLike = articleLikeRepository.save(
                ArticleLike.of(
                        snowflake.nextId(),
                        command.articleId(),
                        command.userId()
                )
        );

        ArticleLikeCount articleLikeCount = articleLikeCountRepository.findById(command.articleId())
                .orElseGet(() -> ArticleLikeCount.of(command.articleId(), 0L));
        articleLikeCount.increase();
        articleLikeCountRepository.save(articleLikeCount);

        outboxEventPublisher.publish(
                EventType.ARTICLE_LIKED,
                ArticleLikedEventPayload.builder()
                        .articleLikeId(articleLike.getArticleLikeId())
                        .articleId(articleLike.getArticleLikeId())
                        .userId(articleLike.getUserId())
                        .createdAt(articleLike.getCreatedAt())
                        .articleLikeCount(count(articleLike.getArticleId()))
                        .build(),
                articleLike.getArticleId()
        );
    }

    @Transactional
    public void unlikeOptimisticLock(ArticleLikeCommand.Unlike command) {
        articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    ArticleLikeCount articleLikeCount = articleLikeCountRepository.findById(command.articleId()).orElseThrow();
                    articleLikeCount.decrease();
                    outboxEventPublisher.publish(
                            EventType.ARTICLE_UNLIKED,
                            ArticleUnlikedEventPayload.builder()
                                    .articleLikeId(articleLike.getArticleLikeId())
                                    .articleId(articleLike.getArticleId())
                                    .userId(articleLike.getUserId())
                                    .createdAt(articleLike.getCreatedAt())
                                    .articleLikeCount(count(articleLike.getArticleId()))
                                    .build(),
                            articleLike.getArticleId()
                    );
                });
    }
}
