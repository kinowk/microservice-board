package microservice.board.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_like_count")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLikeCount {

    @Id
    @Column(name = "article_id", nullable = false, updatable = false)
    private Long articleId; // shard key

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Version
    @Column(name = "version")
    private Long version;

    public static ArticleLikeCount of(Long articleId, Long likeCount) {
        ArticleLikeCount articleLikeCount = new ArticleLikeCount();
        articleLikeCount.articleId = articleId;
        articleLikeCount.likeCount = likeCount;
        return articleLikeCount;
    }

    public void increase() {
        likeCount++;
    }

    public void decrease() {
        likeCount--;
    }
}
