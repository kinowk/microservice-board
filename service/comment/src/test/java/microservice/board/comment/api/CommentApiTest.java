package microservice.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import microservice.board.comment.controller.response.CommentPageResponse;
import microservice.board.comment.controller.response.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

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

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("commentId = " + comment.getCommentId());
        }

        /*
        1번 페이지 수행 결과
        commentId = 337165070354276352
            commentId = 337165070379442176
        commentId = 337165070379442177
            commentId = 337165070379442178
        commentId = 337165070379442179
            commentId = 337165070379442180
        commentId = 337165070383636480
            commentId = 337165070383636481
        commentId = 337165070383636482
            commentId = 337165070383636483
         */
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> response1 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("first page");
        for (CommentResponse response : response1) {
            if (!response.getCommentId().equals(response.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("commentId = " + response.getCommentId());
        }

        Long lastParentCommentId = response1.getLast().getParentCommentId();
        Long lastCommentId = response1.getLast().getCommentId();

        List<CommentResponse> response2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
                        .formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("second page");
        for (CommentResponse response : response2) {
            if (!response.getCommentId().equals(response.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("commentId = " + response.getCommentId());
        }
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
