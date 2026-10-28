package microservice.board.article.api;

import microservice.board.article.controller.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;

class ArticleApiTest {

    RestClient restClient = RestClient.create("http://127.0.0.1:9000");

    @Test
    void createArticle() {
        ArticleRequest.Create request = new ArticleRequest.Create(
                "microservice-board-title1",
                "microservice-board-content1",
                1L,
                1L);
        ArticleResponse.Create response = create(request);
        System.out.println("response = " + response);
        //response = Create[articleId=373127063553466368, title=microservice-board-title1, content=microservice-board-content1, boardId=1, writerId=1, createdAt=2026-10-27T00:13:44.316326, updatedAt=2026-10-27T00:13:44.316344]
    }

    ArticleResponse.Create create(ArticleRequest.Create request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.Create.class);
    }

    @Test
    void readArticle() {
        Long articleId = 373127063553466368L;
        ArticleResponse.Read response = read(articleId);
        System.out.println("response = " + response);
        //response = Read[articleId=373127063553466368, title=microservice-board-title1, content=microservice-board-content1, boardId=1, writerId=1, createdAt=2026-10-27T00:13:44, updatedAt=2026-10-27T00:13:44]
    }

    ArticleResponse.Read read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.Read.class);
    }

    @Test
    void updateArticle() {
        Long articleId = 373127063553466368L;
        ArticleRequest.Update request = new ArticleRequest.Update("microservice-board-title2", "microservice-board-content2");
        ArticleResponse.Update response = update(articleId, request);
        System.out.println("response = " + response);
        //response = Update[articleId=373127063553466368, title=microservice-board-title2, content=microservice-board-content2, boardId=1, writerId=1, createdAt=2026-10-27T00:13:44, updatedAt=2026-10-27T00:15:14.539828]
    }

    ArticleResponse.Update update(Long articleId, ArticleRequest.Update request) {
        return restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(request)
                .retrieve()
                .body(ArticleResponse.Update.class);
    }

    @Test
    void deleteArticle() {
        Long articleId = 373127063553466368L;
        delete(articleId);
    }

    void delete(Long articleId) {
        restClient.delete()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve();
    }

    @Test
    void readAllArticles() {
        ArticleResponse.Page response = readAll();
        List<ArticleResponse.Read> articles = response.articles();
        long count = response.articleCount();
        System.out.println("articles = " + articles);
        System.out.println("count = " + count);
        for (ArticleResponse.Read article : articles) {
            System.out.println("article = " + article);
        }
    }

    ArticleResponse.Page readAll() {
        return restClient.get()
                .uri("/v1/articles?boardId=1&page=300&pageSize=50")
                .retrieve()
                .body(ArticleResponse.Page.class);
    }

    @Test
    void readAllArticlesInfiniteScroll() {
        ArticleResponse.Cursor response1 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=20")
                .retrieve()
                .body(ArticleResponse.Cursor.class);

        System.out.println("response1 = " + response1);

        Long lastArticleId = response1.articles().getLast().articleId();
        ArticleResponse.Cursor response2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=20&lastArticleId=" + lastArticleId)
                .retrieve()
                .body(ArticleResponse.Cursor.class);
        System.out.println("response2 = " + response2);
    }

    static class ArticleRequest {

        record Create(String title, String content, Long boardId, Long writerId) {
        }

        record Update(String title, String content) {
        }
    }

}
