package microservice.board.like.service;

import lombok.RequiredArgsConstructor;
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
        articleLikeRepository.save(
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
                });
    }

    /**
     * select for update
     *
     * @param command
     */
    @Transactional
    public void likePessimisticLock2(ArticleLikeCommand.Like command) {
        articleLikeRepository.save(
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
                });
    }

    @Transactional
    public void likeOptimisticLock(ArticleLikeCommand.Like command) {
        articleLikeRepository.save(
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
    }

    @Transactional
    public void unlikeOptimisticLock(ArticleLikeCommand.Unlike command) {
        articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    ArticleLikeCount articleLikeCount = articleLikeCountRepository.findById(command.articleId()).orElseThrow();
                    articleLikeCount.decrease();
                });
    }
}
