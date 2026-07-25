package microservice.board.comment.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateRequestV2 {

    private Long articleId;
    private String content;
    private String parentPath;
    private Long writerId;
}
