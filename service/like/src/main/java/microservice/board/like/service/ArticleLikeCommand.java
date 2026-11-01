package microservice.board.like.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleLikeCommand {
    public record Read(
            Long articleId,
            Long userId
    ) {
        public static Read of(Long articleId, Long userId) {
            return new Read(articleId, userId);
        }
    }

    public record Like(
            Long articleId,
            Long userId
    ) {
        public static Like of(Long articleId, Long userId) {
            return new Like(articleId, userId);
        }
    }

    public record Unlike(
            Long articleId,
            Long userId
    ) {
        public static Unlike of(Long articleId, Long userId) {
            return new Unlike(articleId, userId);
        }
    }
}
