package microservice.board.article.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleCommand {

    public record Create(
            String title,
            String content,
            Long boradId,
            Long writerId
    ) {
    }

    public record Update(
            String title,
            String content
    ) {
    }
}
