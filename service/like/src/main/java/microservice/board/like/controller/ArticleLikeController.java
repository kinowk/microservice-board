package microservice.board.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.board.like.controller.response.ArticleLikeResponse;
import microservice.board.like.service.ArticleLikeService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse read(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        return articleLikeService.read(articleId, userId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void like(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        articleLikeService.like(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void unlike(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        log.info("unlike articleId={}, userId={}", articleId, userId);
        articleLikeService.unlike(articleId, userId);
    }
}
