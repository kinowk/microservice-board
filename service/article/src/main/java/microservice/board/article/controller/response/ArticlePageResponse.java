package microservice.board.article.controller.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ArticlePageResponse {

    private List<ArticleResponse> articles;
    private Long count;

    public static ArticlePageResponse of(List<ArticleResponse> articles, Long count) {
        ArticlePageResponse response = new ArticlePageResponse();
        response.articles = articles;
        response.count = count;
        return response;
    }
}
