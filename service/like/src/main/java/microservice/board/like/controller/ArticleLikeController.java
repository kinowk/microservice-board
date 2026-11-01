package microservice.board.like.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.like.service.ArticleLikeCommand;
import microservice.board.like.service.ArticleLikeResult;
import microservice.board.like.service.ArticleLikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-likes/aritlces/{articleId}/users/{userId}")
    public ArticleLikeResponse.Read read(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        return ArticleLikeResponse.Read.from(
                articleLikeService.read(
                        ArticleLikeCommand.Read.of(articleId, userId)
                )
        );
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void like(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.like(
                ArticleLikeCommand.Like.of(articleId, userId)
        );
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void unlike(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.unlike(
                ArticleLikeCommand.Unlike.of(articleId, userId)
        );
    }
}
