package microservice.board.comment.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

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

    public record Page(
            List<Read> comments,
            long commentCount
    ) {
        public static Page of(List<Comment> comments, long count) {
            return new Page(
                    comments.stream()
                            .map(Read::from)
                            .toList(),
                    count
            );
        }
    }

    public record Cursor(
            List<Read> comments
    ) {
        public static Cursor of(List<Comment> comments) {
            return new Cursor(
                    comments.stream()
                            .map(Read::from)
                            .toList()
            );
        }
    }
}
