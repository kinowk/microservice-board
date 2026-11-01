package microservice.board.like.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.like.entity.ArticleLike;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleLikeResult {
    public record Read(
            Long articleLikeId,
            Long articleId,
            Long userId,
            LocalDateTime createdAt
    ) {
        public static Read from(ArticleLike articleLike) {
            return new Read(
                    articleLike.getArticleLikeId(),
                    articleLike.getArticleId(),
                    articleLike.getUserId(),
                    articleLike.getCreatedAt()
            );
        }
    }
}
