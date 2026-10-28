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
        long count = articleRepository.count(1L, 100000L);
        log.info("count: {}", count);
    }

    @Test
    void findAllInfiniteScroll() {
        List<Article> articles1 = articleRepository.readAllInfiniteScroll(1L, 20L);
        System.out.println("articles1.size() = " + articles1.size());
        for (Article article : articles1) {
            System.out.println("article = " + article);
        }

        Long lastArticleId = articles1.getLast().getArticleId();
        List<Article> articles2 = articleRepository.readAllInfiniteScroll(1L, 20L, lastArticleId);
        System.out.println("articles2.size() = " + articles2.size());
        for (Article article : articles2) {
            System.out.println("article = " + article);
        }
    }
}