package microservice.board.hotarticle.service.response;

import lombok.Getter;
import microservice.board.hotarticle.client.ArticleClient;

import java.time.LocalDateTime;

@Getter
public class HotArticleResponse {

    private Long articleId;
    private String title;
    private LocalDateTime createdAt;

    public static HotArticleResponse from(ArticleClient.ArticleResponse clientResponse) {
        HotArticleResponse response = new HotArticleResponse();
        response.articleId = clientResponse.getArticleId();
        response.title = clientResponse.getTitle();
        response.createdAt = clientResponse.getCreatedAt();
        return response;
    }
}
