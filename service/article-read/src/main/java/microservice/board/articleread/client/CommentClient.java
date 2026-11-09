package microservice.board.articleread.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentClient {

    private RestClient restClient;

    @Value("${endpoints.microservice-board-comment-service.url}")
    private String commentServiceUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(commentServiceUrl);
    }

    public Long count(Long articleId) {
        try {
            return restClient.get()
                    .uri("/v1/comments/articles/{articleId}/count", articleId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[CommentClient,count] articleId:{}", articleId, e);
            return null;
        }
    }
}
