package microservice.board.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "board_article_count")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardArticleCount {

    @Id
    @Column(name = "board_id", nullable = false, updatable = false)
    private Long boardId; // shard key

    @Column(name = "article_count", nullable = false)
    private Long articleCount;

    public static BoardArticleCount init(Long boardId, Long articleCount) {
        BoardArticleCount boardArticleCount = new BoardArticleCount();
        boardArticleCount.boardId = boardId;
        boardArticleCount.articleCount = articleCount;
        return boardArticleCount;
    }
}
