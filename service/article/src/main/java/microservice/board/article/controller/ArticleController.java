package microservice.board.article.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.article.service.ArticleCommand;
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

    @GetMapping("/v1/articles")
    public ArticleResponse.Page readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        ArticleCommand.Page command = ArticleCommand.Page.of(boardId, page, pageSize);
        ArticleResult.Page result = articleService.readAll(command);
        return ArticleResponse.Page.from(result);
    }

    @GetMapping("/v1/articles/infinite-scroll")
    public ArticleResponse.Cursor readAllInfiniteScroll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(value = "lastArticleId", required = false) Long lastArticleId
    ) {
        ArticleCommand.Cursor command = ArticleCommand.Cursor.of(boardId, pageSize, lastArticleId);
        ArticleResult.Cursor result = articleService.readAllInfiniteScroll(command);
        return ArticleResponse.Cursor.from(result);
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
