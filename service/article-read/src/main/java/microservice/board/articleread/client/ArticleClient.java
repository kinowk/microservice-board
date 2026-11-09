package microservice.board.articleread.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleClient {

    private RestClient restClient;

    @Value("${endpoints.microservice-board-article-service.url}")
    private String articleServiceUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(articleServiceUrl);
    }

    public Optional<ArticleResponse.Read> read(Long articleId) {
        try {
            ArticleResponse.Read response = restClient.get()
                    .uri("/v1/articles/{articleId}", articleId)
                    .retrieve()
                    .body(ArticleResponse.Read.class);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            log.error("[ArticleClient.read] articleId:{}", articleId, e);
            return Optional.empty();
        }
    }

    public static class ArticleResponse {
        public record Read(
                Long articleId,
                String title,
                String content,
                Long boardId,
                Long writerId,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {

        }
    }
}
