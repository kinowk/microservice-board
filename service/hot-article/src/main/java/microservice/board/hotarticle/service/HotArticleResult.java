package microservice.board.hotarticle.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import microservice.board.hotarticle.client.ArticleClient;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotArticleResult {

    public record Read(
            List<ArticleClient.ArticleResponse.Read> responses
    ) {
        public static Read from(List<ArticleClient.ArticleResponse.Read> responses) {
            return new Read(responses);
        }
    }
}
