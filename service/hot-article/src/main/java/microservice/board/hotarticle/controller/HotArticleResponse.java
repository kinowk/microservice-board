package microservice.board.hotarticle.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.hotarticle.service.HotArticleResult;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotArticleResponse {

    public record Read(
            List<Article> articles
    ) {
        public static Read from(HotArticleResult.Read result) {
            return new Read(
                    result.responses().stream()
                            .map(article -> new Article(
                                            article.articleId(),
                                            article.title(),
                                            article.createdAt()
                                    )
                            )
                            .toList()
            );
        }
    }

    public record Article(
            Long articleId,
            String title,
            LocalDateTime createdAt
    ) {
    }
}
