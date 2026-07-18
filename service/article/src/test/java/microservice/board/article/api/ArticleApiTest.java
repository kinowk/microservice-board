package microservice.board.article.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import microservice.board.article.controller.response.ArticlePageResponse;
import microservice.board.article.controller.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createArticle() {
        ArticleResponse response = create(
                new ArticleCreateRequest(
                        "title",
                        "my content",
                        1L,
                        1L
                )
        );

        System.out.println("response = " + response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readArticle() {
        ArticleResponse response = read(333609731439022080L);
        System.out.println("response = " + response);
    }

    ArticleResponse read(Long id) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", id)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateArticle() {
        ArticleResponse updateResponse = update(
                333609731439022080L,
                new ArticleUpdateRequest(
                        "title-2",
                        "my content-2"
                )
        );
        System.out.println("updateResponse = " + updateResponse);

        ArticleResponse readResponse = read(updateResponse.getId());
        System.out.println("readResponse = " + readResponse);
    }

    ArticleResponse update(Long id, ArticleUpdateRequest request) {
        return restClient.put()
                .uri("/v1/articles/{articleId}", id)
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void deleteArticle() {
        delete(333609731439022080L);
    }

    void delete(Long id) {
        restClient.delete()
                .uri("/v1/articles/{articleId}", id)
                .retrieve();
    }

    @Test
    void realAll() {
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response.getArticles() = " + response.getArticles());
        System.out.println("response.getCount() = " + response.getCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("articleId = " + article.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long boardId;
        private Long writerId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }
}
