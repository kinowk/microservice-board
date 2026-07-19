package microservice.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import microservice.board.comment.controller.response.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void createComment() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "content-1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "content-2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "content-3", response1.getCommentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));

//        commentId=337157473994088448
//          commentId=337157475717947392
//          commentId=337157475772473344
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);

    }

    @Test
    void readComment() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 337157473994088448L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void deleteComment() {
//        commentId=337157473994088448
//          commentId=337157475717947392
//          commentId=337157475772473344
        restClient.delete()
                .uri("/v1/comments/{commentId}", 337157475772473344L)
                .retrieve();
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
