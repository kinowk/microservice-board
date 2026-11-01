package microservice.board.comment.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.comment.service.CommentResult;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {
    public record Read(
            Long commentId,
            String content,
            Long articleId,
            Long parentCommentId,
            Long writerId,
            Boolean deleted,
            LocalDateTime createdAt
    ) {
        public static Read from(CommentResult.Read result) {
            return new Read(
                    result.commentId(),
                    result.content(),
                    result.articleId(),
                    result.parentCommentId(),
                    result.writerId(),
                    result.deleted(),
                    result.createdAt()
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
        public static Create from(CommentResult.Create result) {
            return new Create(
                    result.commentId(),
                    result.content(),
                    result.articleId(),
                    result.parentCommentId(),
                    result.writerId(),
                    result.deleted(),
                    result.createdAt()
            );
        }
    }

    public record Page(
            List<Read> comments,
            long commentCount
    ) {
        public static Page from(CommentResult.Page result) {
            return new Page(
                    result.comments().stream()
                            .map(Read::from)
                            .toList(),
                    result.commentCount()
            );
        }
    }

    public record Cursor(
            List<Read> comments
    ) {
        public static Cursor from(CommentResult.Cursor result) {
            return new Cursor(
                    result.comments().stream()
                            .map(Read::from)
                            .toList()
            );
        }
    }
}
