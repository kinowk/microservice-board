package microservice.board.comment.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCommand {
    public record Create(
            String content,
            Long articleId,
            Long parentCommentId,
            Long writerId
    ) {
    }

    public record Page(
            Long articleId,
            Long page,
            Long pageSize
    ) {
        public static Page of(Long articleId, Long page, Long pageSize) {
            return new Page(articleId, page, pageSize);
        }
    }

    public record Cursor(
            Long articleId,
            Long pageSize,
            Long lastParentCommentId,
            Long lastCommentId
    ) {
        public static Cursor of(Long articleId, Long pageSize, Long lastParentCommentId, Long lastCommentId) {
            return new Cursor(
                    articleId,
                    pageSize,
                    lastParentCommentId,
                    lastCommentId
            );
        }
    }
}
