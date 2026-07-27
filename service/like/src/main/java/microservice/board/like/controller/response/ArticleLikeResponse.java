package microservice.board.like.controller.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import microservice.board.like.entity.ArticleLike;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleLikeResponse {

    private Long id;
    private Long articleId;
    private Long userId;
    private LocalDateTime createdAt;

    public static ArticleLikeResponse from(ArticleLike articleLike) {
        ArticleLikeResponse response = new ArticleLikeResponse();
        response.id = articleLike.getId();
        response.articleId = articleLike.getArticleId();
        response.userId = articleLike.getUserId();
        response.createdAt = articleLike.getCreatedAt();
        return response;
    }
}
