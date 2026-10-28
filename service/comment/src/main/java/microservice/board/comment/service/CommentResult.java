package microservice.board.comment.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.comment.entity.Comment;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResult {
    public record Read(
            Long commentId,
            String content,
            Long articleId,
            Long parentCommentId,
            Long writerId,
            Boolean deleted,
            LocalDateTime createdAt
    ) {
        public static Read from(Comment comment) {
            return new Read(
                    comment.getCommentId(),
                    comment.getContent(),
                    comment.getArticleId(),
                    comment.getParentCommentId(),
                    comment.getWriterId(),
                    comment.getDeleted(),
                    comment.getCreatedAt()
            );
        }
    }

    public record Create(
            Long commentId,
            String content,
            Long articleId,
            Long parentCommentId,
            Long writerId,
            Boolean deleted,
            LocalDateTime createdAt
    ) {
        public static Create from(Comment comment) {
            return new Create(
                    comment.getCommentId(),
                    comment.getContent(),
                    comment.getArticleId(),
                    comment.getParentCommentId(),
                    comment.getWriterId(),
                    comment.getDeleted(),
                    comment.getCreatedAt()
            );
        }
    }
}
