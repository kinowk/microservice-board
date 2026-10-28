package microservice.board.comment.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.comment.service.CommentCommand;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentRequest {
    public record Create(
            String content,
            Long articleId,
            Long parentCommentId,
            Long writerId
    ) {
        public CommentCommand.Create toCommand() {
            return new CommentCommand.Create(
                    content,
                    articleId,
                    parentCommentId,
                    writerId
            );
        }
    }
}
