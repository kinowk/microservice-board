package microservice.board.article.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.article.service.ArticleResult;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleResponse {
    public record Read(
            Long articleId,
            String title,
            String content,
            Long boardId,
            Long writerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Read from(ArticleResult.Read result) {
            return new Read(
                    result.articleId(),
                    result.title(),
                    result.content(),
                    result.boardId(),
                    result.writerId(),
                    result.createdAt(),
                    result.updatedAt()
            );
        }
    }

    public record Create(
            Long articleId,
            String title,
            String content,
            Long boardId,
            Long writerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Create from(ArticleResult.Create result) {
            return new Create(
                    result.articleId(),
                    result.title(),
                    result.content(),
                    result.boardId(),
                    result.writerId(),
                    result.createdAt(),
                    result.updatedAt()
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
        public static Update from(ArticleResult.Update result) {
            return new Update(
                    result.articleId(),
                    result.title(),
                    result.content(),
                    result.boardId(),
                    result.writerId(),
                    result.createdAt(),
                    result.updatedAt()
            );
        }
    }

    public record Page(
            List<Read> articles,
            long articleCount
    ) {
        public static Page from(ArticleResult.Page result) {
            return new Page(
                    result.articles().stream()
                            .map(Read::from)
                            .toList(),
                    result.articleCount()
            );
        }
    }

    public record Cursor(
            List<Read> articles
    ) {
        public static Cursor from(ArticleResult.Cursor result) {
            return new Cursor(
                    result.articles()
                            .stream()
                            .map(Read::from)
                            .toList()
            );
        }
    }
}
