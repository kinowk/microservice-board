package microservice.board.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_comment_count")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCommentCount {

    @Id
    @Column(name = "article_id", nullable = false, updatable = false)
    private Long articleId;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    public static ArticleCommentCount of(Long articleId, Long commentCount) {
        ArticleCommentCount articleCommentCount = new ArticleCommentCount();
        articleCommentCount.articleId = articleId;
        articleCommentCount.commentCount = commentCount;
        return articleCommentCount;
    }
}
