package microservice.board.like.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.snowflake.Snowflake;
import microservice.board.like.entity.ArticleLike;
import microservice.board.like.repository.ArticleLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleLikeService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleLikeRepository articleLikeRepository;

    public ArticleLikeResult.Read read(ArticleLikeCommand.Read command) {
        return articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .map(ArticleLikeResult.Read::from)
                .orElseThrow();
    }

    @Transactional
    public void like(ArticleLikeCommand.Like command) {
        articleLikeRepository.save(
                ArticleLike.of(
                        snowflake.nextId(),
                        command.articleId(),
                        command.userId()
                )
        );
    }

    @Transactional
    public void unlike(ArticleLikeCommand.Unlike command) {
        articleLikeRepository.findByArticleIdAndUserId(command.articleId(), command.userId())
                .ifPresent(articleLikeRepository::delete);
    }
}
