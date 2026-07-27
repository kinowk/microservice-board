package microservice.board.like.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "article_like")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLike {

    @Id
    @Column(name = "article_like_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "article_id", nullable = false, updatable = false)
    private Long articleId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static ArticleLike create(Long id, Long articleId, Long userId) {
        ArticleLike articleLike = new ArticleLike();
        articleLike.id = id;
        articleLike.articleId = articleId;
        articleLike.userId = userId;
        articleLike.createdAt = LocalDateTime.now();
        return articleLike;
    }
}
