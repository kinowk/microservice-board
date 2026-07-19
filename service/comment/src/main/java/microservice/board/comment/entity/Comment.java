package microservice.board.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "article_id", nullable = false, updatable = false)
    private Long articleId; // shard key

    @Column(name = "parent_comment_id", nullable = false, updatable = false)
    private Long parentCommentId;

    @Column(name = "writer_id", nullable = false, updatable = false)
    private Long writerId;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static Comment create(Long id, String content, Long articleId, Long parentCommentId, Long writerId) {
        Comment comment = new Comment();
        comment.commentId = id;
        comment.content = content;
        comment.articleId = articleId;
        comment.parentCommentId = parentCommentId == null ? id : parentCommentId;
        comment.writerId = writerId;
        comment.deleted = false;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }

    public boolean isRoot() {
        return this.parentCommentId.longValue() == this.commentId;
    }

    public void delete() {
        this.deleted = true;
    }
}
