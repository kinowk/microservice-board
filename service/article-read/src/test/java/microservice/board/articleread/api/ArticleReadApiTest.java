package microservice.board.articleread.api;

import microservice.board.articleread.service.response.ArticleReadPageResponse;
import microservice.board.articleread.service.response.ArticleReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

class ArticleReadApiTest {

    RestClient articleReadRestClient = RestClient.create("http://localhost:9005");
    RestClient articleRestClient = RestClient.create("http://localhost:9000");

    @Test
    void readTest() {
        ArticleReadResponse response = articleReadRestClient.get()
                .uri("/v1/articles/{articleId}", 348828251455344640L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void readAllTest() {
        ArticleReadPageResponse response1 = articleReadRestClient.get()
                .uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 1L, 5L))
                .retrieve()
                .body(ArticleReadPageResponse.class);
        System.out.println("response1 = " + response1);
        for (ArticleReadResponse article : response1.getArticles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }

        ArticleReadPageResponse response2 = articleRestClient.get()
                .uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 1L, 5L))
                .retrieve()
                .body(ArticleReadPageResponse.class);
        System.out.println("response2 = " + response2);
        for (ArticleReadResponse article : response2.getArticles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }
    }

    @Test
    void readAllInfiniteScroll() {
        List<ArticleReadResponse> response1 = articleReadRestClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=%s&pageSize=%s".formatted(1L, 5L))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleReadResponse>>() {
                });
        System.out.println("response1 = " + response1);
        for (ArticleReadResponse response : response1) {
            System.out.println("response.getArticleId() = " + response.getArticleId());
        }

        List<ArticleReadResponse> response2 = articleRestClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=%s&pageSize=%s".formatted(1L, 5L))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleReadResponse>>() {
                });
        System.out.println("response2 = " + response2);
        for (ArticleReadResponse response : response2) {
            System.out.println("response.getArticleId() = " + response.getArticleId());
        }
    }
}
