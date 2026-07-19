package microservice.board.comment.controller.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CommentPageResponse {

    private List<CommentResponse> comments;
    private Long commentCount;

    public static CommentPageResponse of(List<CommentResponse> comments, Long count) {
        CommentPageResponse response = new CommentPageResponse();
        response.comments = comments;
        response.commentCount = count;
        return response;
    }
}
