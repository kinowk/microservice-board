package microservice.board.article.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import microservice.board.article.controller.response.ArticlePageResponse;
import microservice.board.article.controller.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

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

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
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

        ArticleResponse readResponse = read(updateResponse.getArticleId());
        System.out.println("readResponse = " + readResponse);
    }

    ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        return restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void deleteArticle() {
        delete(333609731439022080L);
    }

    void delete(Long articleId) {
        restClient.delete()
                .uri("/v1/articles/{articleId}", articleId)
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
            System.out.println("articleId = " + article.getArticleId());
        }
    }

    @Test
    void readAllInfiniteScroll() {
        List<ArticleResponse> articles1 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        System.out.println("first page");
        for (ArticleResponse response : articles1) {
            System.out.println("response.articleId() = " + response.getArticleId());
        }

        Long lastArticleId = articles1.getLast().getArticleId();
        List<ArticleResponse> articles2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });
        for (ArticleResponse response : articles2) {
            System.out.println("response.articleId() = " + response.getArticleId());
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
