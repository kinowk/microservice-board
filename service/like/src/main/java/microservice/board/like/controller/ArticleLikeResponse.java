package microservice.board.like.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.like.service.ArticleLikeResult;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleLikeResponse {
    public record Read(
            Long articleLikeId,
            Long articleId,
            Long userId,
            LocalDateTime createdAt
    ) {
        public static Read from(ArticleLikeResult.Read result) {
            return new Read(
                    result.articleLikeId(),
                    result.articleId(),
                    result.userId(),
                    result.createdAt()
            );
        }
    }
}
