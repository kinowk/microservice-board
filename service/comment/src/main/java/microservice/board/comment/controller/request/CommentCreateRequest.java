package microservice.board.comment.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateRequest {

    private Long articleId;
    private String content;
    private Long parentCommentId;
    private Long writerId;
}
