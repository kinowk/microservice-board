package microservice.board.article.controller.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import microservice.board.article.entity.Article;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleResponse {

    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ArticleResponse from(Article article) {
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.articleId = article.getArticleId();
        articleResponse.title = article.getTitle();
        articleResponse.content = article.getContent();
        articleResponse.boardId = article.getBoardId();
        articleResponse.writerId = article.getWriterId();
        articleResponse.createdAt = article.getCreatedAt();
        articleResponse.updatedAt = article.getUpdatedAt();
        return articleResponse;
    }
}
