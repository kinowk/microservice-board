package microservice.board.view.service;

import lombok.RequiredArgsConstructor;
import microservice.board.view.repository.ArticleViewCountRepository;
import microservice.board.view.repository.ArticleViewDistributedLockRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private static final int BACKUP_BATCH_SIZE = 100;
    private static final Duration TTL = Duration.ofMinutes(5);

    private final ArticleViewCountBackupProcessor articleViewCountBackupProcessor;
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewDistributedLockRepository articleViewDistributedLockRepository;

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }

    public Long increase(Long articleId, Long userId) {
        if (!articleViewDistributedLockRepository.lock(articleId, userId, TTL)) {
            return count(articleId);
        }

        Long viewCount = articleViewCountRepository.increase(articleId, userId);
        if (viewCount % BACKUP_BATCH_SIZE == 0) {
            articleViewCountBackupProcessor.backup(articleId, viewCount);
        }
        return viewCount;
    }
}
