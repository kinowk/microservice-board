package microservice.board.like.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.like.service.ArticleLikeCommand;
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

    @GetMapping("/v1/article-likes/articles/{articleId}/count")
    public long count(@RequestParam("articleId") Long articleId) {
        return articleLikeService.count(articleId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-1")
    public void likePessimisticLock1(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.likePessimisticLock1(
                ArticleLikeCommand.Like.of(articleId, userId)
        );
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-1")
    public void unlikePessimisticLock1(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.unlikePessimisticLock1(
                ArticleLikeCommand.Unlike.of(articleId, userId)
        );
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-2")
    public void likePessimisticLock2(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.likePessimisticLock2(
                ArticleLikeCommand.Like.of(articleId, userId)
        );
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-2")
    public void unlikePessimisticLock2(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.unlikePessimisticLock2(
                ArticleLikeCommand.Unlike.of(articleId, userId)
        );
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/optimistic-lock")
    public void likeOptimisticLock(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.likeOptimisticLock(
                ArticleLikeCommand.Like.of(articleId, userId)
        );
    }

    @DeleteMapping("/v1/article-likes/articles/{articleid}/users/{userId}/optimistic-lock")
    public void unlikeOptimisticLock(
            @RequestParam("articleId") Long articleId,
            @RequestParam("userId") Long userId
    ) {
        articleLikeService.unlikeOptimisticLock(
                ArticleLikeCommand.Unlike.of(articleId, userId)
        );
    }
}
