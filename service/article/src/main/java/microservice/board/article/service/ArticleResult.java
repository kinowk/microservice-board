package microservice.board.article.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.article.entity.Article;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleResult {
    public record Create(
            Long articleId,
            String title,
            String content,
            Long boardId,
            Long writerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Create from(Article article) {
            return new Create(
                    article.getArticleId(),
                    article.getTitle(),
                    article.getContent(),
                    article.getBoardId(),
                    article.getWriterId(),
                    article.getCreatedAt(),
                    article.getUpdatedAt()
            );
        }
    }

    public record Read(
            Long articleId,
            String title,
            String content,
            Long boardId,
            Long writerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Read from(Article article) {
            return new Read(
                    article.getArticleId(),
                    article.getTitle(),
                    article.getContent(),
                    article.getBoardId(),
                    article.getWriterId(),
                    article.getCreatedAt(),
                    article.getUpdatedAt()
            );
        }
    }

    public record Update(
            Long articleId,
            String title,
            String content,
            Long boardId,
            Long writerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Update from(Article article) {
            return new Update(
                    article.getArticleId(),
                    article.getTitle(),
                    article.getContent(),
                    article.getBoardId(),
                    article.getWriterId(),
                    article.getCreatedAt(),
                    article.getUpdatedAt()
            );
        }
    }

    public record Page(
            List<Read> articles,
            long articleCount
    ) {
        public static Page of(List<Article> articles, long articleCount) {
            return new Page(
                    articles.stream()
                            .map(Read::from)
                            .toList(),
                    articleCount
            );
        }
    }
}
