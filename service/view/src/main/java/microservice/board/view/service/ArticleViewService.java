package microservice.board.view.service;

import lombok.RequiredArgsConstructor;
import microservice.board.view.repository.ArticleViewCountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private static final int BACKUP_BATCH_SIZE = 100;

    private final ArticleViewCountBackupProcessor articleViewCountBackupProcessor;
    private final ArticleViewCountRepository articleViewCountRepository;

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }

    public Long increase(Long articleId, Long userId) {
        Long viewCount = articleViewCountRepository.increase(articleId, userId);
        if (viewCount % BACKUP_BATCH_SIZE == 0) {
            articleViewCountBackupProcessor.backup(articleId, viewCount);
        }
        return viewCount;
    }
}
