package microservice.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import microservice.board.comment.controller.response.CommentPageResponse;
import microservice.board.comment.controller.response.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiV2Test {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void createComment() {
        CommentResponse response1 = create(new CommentCreateRequestV2(1L, "content-v2-1", null, 1L));
        CommentResponse response2 = create(new CommentCreateRequestV2(1L, "content-v2-2", response1.getPath(), 1L));
        CommentResponse response3 = create(new CommentCreateRequestV2(1L, "content-v2-3", response2.getPath(), 1L));

        System.out.println("response1.getCommentId() = " + response1.getCommentId());
        System.out.println("\tresponse2.getCommentId() = " + response2.getCommentId());
        System.out.println("\t\tresponse3.getCommentId() = " + response3.getCommentId());
        System.out.println("response1.getPath() = " + response1.getPath());
        System.out.println("\tresponse2.getPath() = " + response2.getPath());
        System.out.println("\t\tresponse3.getPath() = " + response3.getPath());

        /*
        response1.getCommentId() = 339409199143821312
            response2.getCommentId() = 339409199483559936
                response3.getCommentId() = 339409199554863104
        response1.getPath() = 00002
            response2.getPath() = 0000200000
                response3.getPath() = 000020000000000
         */
    }

    CommentResponse create(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void readComment() {
        CommentResponse response = restClient.get()
                .uri("/v2/comments/{commentId}", 339409199554863104L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("response = " + response);
    }

    @Test
    void deleteComment() {
        restClient.delete()
                .uri("/v2/comments/{commentId}", 339409199483559936L)
                .retrieve();
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v2/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response = " + response);
        for (CommentResponse comment : response.getComments()) {
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        /*
        comment.getCommentId() = 339404434112487424
        comment.getCommentId() = 339404435039428608
        comment.getCommentId() = 339404435131703296
        comment.getCommentId() = 339407879007608832
        comment.getCommentId() = 339407879359930368
        comment.getCommentId() = 339407879422844928
        comment.getCommentId() = 339409199143821312
        comment.getCommentId() = 339409199483559936
        comment.getCommentId() = 339409199554863104
         */
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> response1 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("first page");
        for (CommentResponse response : response1) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }

        String lastPath = response1.getLast().getPath();
        List<CommentResponse> response2 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId=1&pageSize=5&lastPath=%s".formatted(lastPath))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("second page");
        for (CommentResponse response : response2) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }

        /*
        first page
        response.getCommentId() = 339404434112487424
        response.getCommentId() = 339404435039428608
        response.getCommentId() = 339404435131703296
        response.getCommentId() = 339407879007608832
        response.getCommentId() = 339407879359930368
        second page
        response.getCommentId() = 339407879422844928
        response.getCommentId() = 339409199143821312
        response.getCommentId() = 339409199483559936
        response.getCommentId() = 339409199554863104
         */
    }

    @Test
    void countTest() {
        CommentResponse response = create(new CommentCreateRequestV2(2L, "count-test-content", null, 1L));

        Long count1 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", 2L)
                .retrieve()
                .body(Long.class);
        System.out.println("count1 = " + count1);

        restClient.delete()
                .uri("/v2/comments/{commetId}", response.getCommentId())
                .retrieve();

        Long count2 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", 2L)
                .retrieve()
                .body(Long.class);
        System.out.println("count2 = " + count2);
    }

    @Getter
    @AllArgsConstructor
    public class CommentCreateRequestV2 {
        private Long articleId;
        private String content;
        private String parentPath;
        private Long writerId;
    }
}
