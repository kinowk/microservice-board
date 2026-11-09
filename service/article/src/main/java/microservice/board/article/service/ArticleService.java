package microservice.board.article.service;

import lombok.RequiredArgsConstructor;
import microservice.board.article.entity.Article;
import microservice.board.article.entity.BoardArticleCount;
import microservice.board.article.repository.ArticleRepository;
import microservice.board.article.repository.BoardArticleCountRepository;
import microservice.board.common.event.EventType;
import microservice.board.common.event.payload.ArticleCreatedEventPayload;
import microservice.board.common.event.payload.ArticleDeletedEventPayload;
import microservice.board.common.event.payload.ArticleUpdatedEventPayload;
import microservice.board.common.outboxmessagerelay.OutboxEventPublisher;
import microservice.board.common.snowflake.Snowflake;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public ArticleResult.Create create(ArticleCommand.Create command) {
        Article article = Article.of(
                snowflake.nextId(),
                command.title(),
                command.content(),
                command.boradId(),
                command.writerId()
        );
        articleRepository.save(article);

        int result = boardArticleCountRepository.increase(article.getBoardId());
        if (result == 0) {
            boardArticleCountRepository.save(
                    BoardArticleCount.of(article.getBoardId(), 1L)
            );
        }

        outboxEventPublisher.publish(
                EventType.ARTICLE_CREATED,
                ArticleCreatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .boardArticleCount(count(article.getBoardId()))
                        .build(),
                article.getBoardId()
        );

        return ArticleResult.Create.from(article);
    }

    public ArticleResult.Read read(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        return ArticleResult.Read.from(article);
    }

    @Transactional
    public ArticleResult.Update update(Long articleId, ArticleCommand.Update command) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(command.title(), command.content());

        outboxEventPublisher.publish(
                EventType.ARTICLE_UPDATED,
                ArticleUpdatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .build(),
                article.getBoardId()
        );
        return ArticleResult.Update.from(article);
    }

    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        articleRepository.delete(article);

        outboxEventPublisher.publish(
                EventType.ARTICLE_DELETED,
                ArticleDeletedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .boardArticleCount(count(article.getBoardId()))
                        .build(),
                article.getBoardId()
        );

        boardArticleCountRepository.decrease(article.getBoardId());
    }

    public ArticleResult.Page readAll(ArticleCommand.Page command) {
        return ArticleResult.Page.of(
                articleRepository.findAll(
                        command.boardId(),
                        (command.page() - 1) * command.pageSize(), command.pageSize()
                ),
                articleRepository.count(
                        command.boardId(),
                        PageLimitCalculator.calculatePageLimit(command.page(), command.pageSize(), 10L)
                )
        );
    }

    public ArticleResult.Cursor readAllInfiniteScroll(ArticleCommand.Cursor command) {
        List<Article> articles = command.lastArticleId() == null ?
                articleRepository.readAllInfiniteScroll(command.boardId(), command.pageSize()) :
                articleRepository.readAllInfiniteScroll(command.boardId(), command.pageSize(), command.lastArticleId());
        return ArticleResult.Cursor.from(articles);
    }

    public Long count(Long boardId) {
        return boardArticleCountRepository.findById(boardId)
                .map(BoardArticleCount::getArticleCount)
                .orElseThrow();
    }
}
