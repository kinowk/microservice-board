package microservice.board.hotarticle.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.hotarticle.service.HotArticleResult;
import microservice.board.hotarticle.service.HotArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotArticleController {

    private final HotArticleService hotArticleService;

    @GetMapping("/v1/hot-articles/articles/date/{date}")
    public HotArticleResponse.Read read(@PathVariable String date) {
        HotArticleResult.Read result = hotArticleService.readAll(date);
        return HotArticleResponse.Read.from(result);
    }
}
