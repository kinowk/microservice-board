package microservice.board.article.service;

import lombok.RequiredArgsConstructor;
import microservice.board.article.controller.request.ArticleCreateRequest;
import microservice.board.article.controller.request.ArticleUpdateRequest;
import microservice.board.article.controller.response.ArticleResponse;
import microservice.board.article.entity.Article;
import microservice.board.article.repository.ArticleRepository;
import microservice.board.common.snowflake.Snowflake;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId())
        );
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        return ArticleResponse.from(article);
    }

    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        articleRepository.delete(article);
    }
}
