package microservice.board.hotarticle.api;

import microservice.board.hotarticle.service.response.HotArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

class HotArticleApiTest {
    RestClient restclient = RestClient.create("http://localhost:9004");

    @Test
    void readAllTest() {
        List<HotArticleResponse> responses = restclient.get()
                .uri("/v1/hot-articles/articles/date/{date}", "20260817")
                .retrieve()
                .body(new ParameterizedTypeReference<List<HotArticleResponse>>() {
                });
        for (HotArticleResponse respons : responses) {
            System.out.println("respons = " + respons);
        }
    }
}
