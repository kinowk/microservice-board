package microservice.board.articleread.repository;

import lombok.Getter;
import microservice.board.articleread.client.ArticleClient;
import microservice.board.common.event.payload.*;

import java.time.LocalDateTime;

@Getter
public class ArticleQueryModel {

    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long articleCommentCount;
    private Long articleLikeCount;

    public static ArticleQueryModel of(ArticleCreatedEventPayload payload) {
        ArticleQueryModel articleQueryModel = new ArticleQueryModel();
        articleQueryModel.articleId = payload.getArticleId();
        articleQueryModel.title = payload.getTitle();
        articleQueryModel.content = payload.getContent();
        articleQueryModel.boardId = payload.getBoardId();
        articleQueryModel.writerId = payload.getWriterId();
        articleQueryModel.createdAt = payload.getCreatedAt();
        articleQueryModel.updatedAt = payload.getUpdatedAt();
        articleQueryModel.articleCommentCount = 0L;
        articleQueryModel.articleLikeCount = 0L;
        return articleQueryModel;
    }

    public static ArticleQueryModel of(ArticleClient.ArticleResponse.Read article, Long commentCount, Long likeCount) {
        ArticleQueryModel articleQueryModel = new ArticleQueryModel();
        articleQueryModel.articleId = article.articleId();
        articleQueryModel.title = article.title();
        articleQueryModel.content = article.content();
        articleQueryModel.boardId = article.boardId();
        articleQueryModel.writerId = article.writerId();
        articleQueryModel.createdAt = article.createdAt();
        articleQueryModel.updatedAt = article.updatedAt();
        articleQueryModel.articleCommentCount = commentCount;
        articleQueryModel.articleLikeCount = likeCount;
        return articleQueryModel;
    }

    public void updateBy(ArticleUpdatedEventPayload payload) {
        this.title = payload.getTitle();
        this.content = payload.getContent();
        this.boardId = payload.getBoardId();
        this.writerId = payload.getWriterId();
        this.createdAt = payload.getCreatedAt();
        this.updatedAt = payload.getUpdatedAt();
    }

    public void updateBy(CommentCreatedEventPayload payload) {
        this.articleCommentCount = payload.getArticleCommentCount();
    }

    public void updateBy(CommentDeletedEventPayload payload) {
        this.articleCommentCount = payload.getArticleCommentCount();
    }

    public void updateBy(ArticleLikedEventPayload payload) {
        this.articleLikeCount = payload.getArticleLikeCount();
    }

    public void updateBy(ArticleUnlikedEventPayload payload) {
        this.articleLikeCount = payload.getArticleLikeCount();
    }
}
