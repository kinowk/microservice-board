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
}
