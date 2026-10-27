package microservice.board.article.service;

import lombok.RequiredArgsConstructor;
import microservice.board.article.entity.Article;
import microservice.board.article.repository.ArticleRepository;
import microservice.board.common.snowflake.Snowflake;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

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
        return ArticleResult.Update.from(article);
    }

    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        articleRepository.delete(article);
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
}
