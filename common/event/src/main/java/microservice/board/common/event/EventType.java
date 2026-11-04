package microservice.board.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.board.common.event.payload.*;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {

    ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.MICROSERVICE_BOARD_ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.MICROSERVICE_BOARD_ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.MICROSERVICE_BOARD_ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.MICROSERVICE_BOARD_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.MICROSERVICE_BOARD_COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.MICROSERVICE_BOARD_LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.MICROSERVICE_BOARD_LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.MICROSERVICE_BOARD_VIEW);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (IllegalArgumentException e) {
            log.error("[EventType.from] type:{}", type, e);
            return null;
        }
    }

    /*
    /opt/kafka/bin $ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic microservice-board-article --replication-factor 1 --partitions 3
    Created topic microservice-board-article.

    /opt/kafka/bin $ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic microservice-board-comment --replication-factor 1 --partitions 3
    Created topic microservice-board-comment.

    /opt/kafka/bin $ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic microservice-board-like --replication-factor 1 --partitions 3
    Created topic microservice-board-like.

    /opt/kafka/bin $ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic microservice-board-view --replication-factor 1 --partitions 3
    Created topic microservice-board-view.
    */
    public static class Topic {
        public static final String MICROSERVICE_BOARD_ARTICLE = "microservice-board-article";
        public static final String MICROSERVICE_BOARD_COMMENT = "microservice-board-comment";
        public static final String MICROSERVICE_BOARD_LIKE = "microservice-board-like";
        public static final String MICROSERVICE_BOARD_VIEW = "microservice-board-view";
    }
}
