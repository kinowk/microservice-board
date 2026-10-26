package microservice.board.article.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.article.service.ArticleResult;
import microservice.board.article.service.ArticleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse.Read read(@PathVariable("articleId") Long articleId) {
        ArticleResult.Read result = articleService.read(articleId);
        return ArticleResponse.Read.from(result);
    }

    @PostMapping("/v1/articles")
    public ArticleResponse.Create create(@RequestBody ArticleRequest.Create request) {
        ArticleResult.Create result = articleService.create(request.toCommand());
        return ArticleResponse.Create.from(result);
    }

    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse.Update update(@PathVariable("articleId") Long articleId, @RequestBody ArticleRequest.Update request) {
        ArticleResult.Update result = articleService.update(articleId, request.toCommand());
        return ArticleResponse.Update.from(result);
    }

    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable("articleId") Long articleId) {
        articleService.delete(articleId);
    }

}
