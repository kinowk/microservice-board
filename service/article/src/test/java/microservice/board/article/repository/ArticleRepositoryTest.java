package microservice.board.article.repository;

import lombok.extern.slf4j.Slf4j;
import microservice.board.article.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findAll() {
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);
        log.info("articles.size: {}", articles.size());
        for (Article article : articles) {
            log.info("article: {}", article);
        }
    }

    @Test
    void count() {
        Long count = articleRepository.count(1L, 10000L);
        log.info("count: {}", count);
    }

    @Test
    void findAllInfiniteScroll() {
        List<Article> articles1 = articleRepository.findAllInfiniteScroll(1L, 30L);
        for (Article article : articles1) {
            log.info("articleId: {}", article.getId());
        }

        Long lastArticleId = articles1.getLast().getId();
        List<Article> articles2 = articleRepository.findAllInfiniteScroll(1L, 30L, lastArticleId);
        for (Article article : articles2) {
            log.info("articleId: {}", article.getId());
        }
    }
}