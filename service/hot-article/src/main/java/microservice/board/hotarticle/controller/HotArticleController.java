package microservice.board.hotarticle.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.hotarticle.service.HotArticleService;
import microservice.board.hotarticle.service.response.HotArticleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotArticleController {

    private final HotArticleService hotArticleService;

    @GetMapping("/v1/hot-articles/articles/date/{date}")
    public List<HotArticleResponse> readAll(
            @PathVariable("date") String date
    ) {
        return hotArticleService.readAll(date);
    }
}
