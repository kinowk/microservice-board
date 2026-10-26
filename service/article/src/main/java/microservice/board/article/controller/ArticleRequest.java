package microservice.board.article.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.article.service.ArticleCommand;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleRequest {
    public record Create(
            String title,
            String content,
            Long boardId,
            Long writerId
    ) {
        public ArticleCommand.Create toCommand() {
            return new ArticleCommand.Create(title, content, boardId, writerId);
        }
    }

    public record Update(
            String title,
            String content
    ) {
        public ArticleCommand.Update toCommand() {
            return new ArticleCommand.Update(title, content);
        }
    }
}
