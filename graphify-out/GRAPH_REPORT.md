# Graph Report - microservice-board  (2026-10-28)

## Corpus Check
- Scanned the repository root: 170 code/configuration files, approximately 16,820 words.
- The corpus fits in one context window; the graph was explicitly requested for navigation.
- No documentation, papers, images, or videos were eligible for semantic extraction.

## Coverage and Validation
- Local AST/configuration extraction; no external LLM calls. Community names were curated by the host agent.
- Eleven Gradle build files yielded no symbols and remain file nodes; Gradle module dependencies are not fully represented.
- Six unsupported files were skipped, including Gradle wrapper metadata and Spring auto-configuration imports. Ignored files and generated build directories were excluded.
- Post-build diagnostics: zero missing/dangling endpoints, zero duplicate edges, and 12 self-loops. Raw extraction edge collapse cannot be reconstructed from the exported graph.
- Graph query smoke check passed for `ArticleReadService` and `Event`.
- Browser automation timed out twice; visual rendering and interactions were not verified. The HTML loads vis-network from unpkg.com and requires network access on first load.
- Tool benchmark using the measured 16,820-word corpus: approximately 22,426 baseline tokens versus 1,620 tokens per sample query, a 13.8x estimated reduction. This is a synthetic estimate, not measured model usage.
- `graphify-out/` was written into the working tree and is currently untracked. Source files were not changed.

## Summary
- 1034 nodes · 3020 edges · 57 communities (30 shown, 27 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 161 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f1df6f87`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- JPA Entities and Persistence
- Event Payloads and Consumers
- JPA Repositories and Queries
- Article Read Query Models
- Article Read HTTP Clients
- Outbox Relay and Sharding
- Kafka and Executor Configuration
- Comment HTTP Controllers
- Event Types and Dispatch
- Repository and Numeric Tests
- Read and View Controllers
- Event Serialization and Publishing
- Integration Data Initializers
- Article Read Service Configuration
- Comment V2 Creation
- JSON Serialization and Responses
- Optimized Cache Locking
- Spring Application Entry Points
- Article Service Configuration
- Comment Service Configuration
- Like Service Configuration
- View Service Configuration
- Transactional Article Operations
- Comment Path Hierarchy
- Hot Article Service Configuration
- Article Pagination API Tests
- Hot Article Ranking Storage
- Like Concurrency HTTP API
- Redis Counters and Expiration
- Optimized Cache Annotation Aspect
- Article API Tests
- Domain Services and Identifiers
- Hot Article Score Calculation
- Article Creation Event Handlers
- Shard and Cache TTL Tests
- Article Deletion Event Handlers
- Comment V2 API Tests
- Cache Data Model Tests
- Article Identifier Pagination
- Snowflake Identifier Tests
- Hot Article Event Tests
- Like API Tests
- Board Article Count Storage

## God Nodes (most connected - your core abstractions)
1. `Event` - 76 edges
2. `EventType` - 44 edges
3. `EventPayload` - 34 edges
4. `CommentResponse` - 27 edges
5. `ArticleQueryModelRepository` - 26 edges
6. `ArticleReadService` - 24 edges
7. `Comment` - 23 edges
8. `CommentV2` - 22 edges
9. `CommentPath` - 22 edges
10. `ArticleResponse` - 21 edges

## Surprising Connections (you probably didn't know these)
- `ArticleLikedEventHandler` --references--> `ArticleLikedEventPayload`  [EXTRACTED]
  service/article-read/src/main/java/microservice/board/articleread/service/event/handler/ArticleLikedEventHandler.java → common/event/src/main/java/microservice/board/common/event/payload/ArticleLikedEventPayload.java
- `ArticleUnlikedEventHandler` --references--> `ArticleUnlikedEventPayload`  [EXTRACTED]
  service/article-read/src/main/java/microservice/board/articleread/service/event/handler/ArticleUnlikedEventHandler.java → common/event/src/main/java/microservice/board/common/event/payload/ArticleUnlikedEventPayload.java
- `ArticleUpdatedEventHandler` --references--> `ArticleUpdatedEventPayload`  [EXTRACTED]
  service/article-read/src/main/java/microservice/board/articleread/service/event/handler/ArticleUpdatedEventHandler.java → common/event/src/main/java/microservice/board/common/event/payload/ArticleUpdatedEventPayload.java
- `CommentCreatedEventHandler` --references--> `CommentCreatedEventPayload`  [EXTRACTED]
  service/article-read/src/main/java/microservice/board/articleread/service/event/handler/CommentCreatedEventHandler.java → common/event/src/main/java/microservice/board/common/event/payload/CommentCreatedEventPayload.java
- `CommentDeletedEventHandler` --references--> `CommentDeletedEventPayload`  [EXTRACTED]
  service/article-read/src/main/java/microservice/board/articleread/service/event/handler/CommentDeletedEventHandler.java → common/event/src/main/java/microservice/board/common/event/payload/CommentDeletedEventPayload.java

## Import Cycles
- None detected.

## Communities (57 total, 27 thin omitted)

### Community 0 - "JPA Entities and Persistence"
Cohesion: 0.07
Nodes (18): EventRaw, MessageRelayConstants, Outbox, OutboxEvent, OptimizedCacheTTL, ArticleController, ArticleCreateRequest, ArticleUpdateRequest (+10 more)

### Community 1 - "Event Payloads and Consumers"
Cohesion: 0.06
Nodes (22): EventPayload, ArticleLikedEventPayload, ArticleUnlikedEventPayload, ArticleUpdatedEventPayload, ArticleViewedEventPayload, CommentCreatedEventPayload, CommentDeletedEventPayload, ArticlePageResponse (+14 more)

### Community 2 - "JPA Repositories and Queries"
Cohesion: 0.08
Nodes (11): OutboxRepository, BoardArticleCount, ArticleRepository, BoardArticleCountRepository, Comment, ArticleCommentCountRepository, CommentRepository, CommentServiceTest (+3 more)

### Community 3 - "Article Read Query Models"
Cohesion: 0.10
Nodes (8): ArticleQueryModel, ArticleQueryModelRepository, ArticleLikedEventHandler, ArticleUnlikedEventHandler, ArticleUpdatedEventHandler, CommentCreatedEventHandler, CommentDeletedEventHandler, EventHandler

### Community 4 - "Article Read HTTP Clients"
Cohesion: 0.14
Nodes (10): ArticleClient, ParameterizedTypeReference<List<ArticleResponse>>, ArticleResponse, CommentClient, LikeClient, ViewClient, ArticleReadEventConsumer, ArticleReadService (+2 more)

### Community 5 - "Outbox Relay and Sharding"
Cohesion: 0.10
Nodes (3): AssignedShard, MessageRelay, MessageRelayCoordinator

### Community 6 - "Kafka and Executor Configuration"
Cohesion: 0.11
Nodes (4): MessageRelayConfig, CacheConfig, KafkaConfig, KafkaConfig

### Community 7 - "Comment HTTP Controllers"
Cohesion: 0.14
Nodes (6): CommentController, CommentControllerV2, CommentCreateRequest, CommentPageResponse, CommentResponse, CommentService

### Community 8 - "Event Types and Dispatch"
Cohesion: 0.19
Nodes (12): EventType, ARTICLE_CREATED, ARTICLE_DELETED, ARTICLE_LIKED, ARTICLE_UNLIKED, ARTICLE_UPDATED, ARTICLE_VIEWED, COMMENT_CREATED (+4 more)

### Community 9 - "Repository and Numeric Tests"
Cohesion: 0.10
Nodes (9): ViewClientTest, LongToDoubleTest, ArticleRepositoryTest, PageLimitCalculatorTest, CommentApiTest, ParameterizedTypeReference<List<CommentResponse>>, ParameterizedTypeReference<List<CommentResponse>>, CommentCreateRequest (+1 more)

### Community 10 - "Read and View Controllers"
Cohesion: 0.14
Nodes (6): ArticleReadController, ArticleReadPageResponse, ArticleReadResponse, HotArticleController, ArticleViewController, ArticleViewService

### Community 11 - "Event Serialization and Publishing"
Cohesion: 0.13
Nodes (5): Event, EventTest, HotArticleEventConsumer, EventHandler, HotArticleService

### Community 12 - "Integration Data Initializers"
Cohesion: 0.20
Nodes (4): DataInitializer, DataInitializer, DataInitializerV2, ArticleViewCountBackUpRepositoryTest

### Community 13 - "Article Read Service Configuration"
Cohesion: 0.08
Nodes (24): name, enable-auto-commit, group-id, key-deserializer, value-deserializer, redis, endpoints, microservice-board-article-service (+16 more)

### Community 14 - "Comment V2 Creation"
Cohesion: 0.16
Nodes (4): CommentCreateRequestV2, CommentV2, CommentRepositoryV2, CommentServiceV2

### Community 16 - "Optimized Cache Locking"
Cohesion: 0.22
Nodes (4): OptimizedCacheLockProvider, OptimizedCacheManager, OptimizedCacheOriginDataSupplier, OptimizedCacheManagerTest

### Community 17 - "Spring Application Entry Points"
Cohesion: 0.20
Nodes (6): ArticleReadApplication, ArticleApplication, CommentApplication, HotArticleApplication, LikeApplication, ViewApplication

### Community 18 - "Article Service Configuration"
Cohesion: 0.09
Nodes (21): name, redis, driver-class-name, password, url, username, ddl-auto, database-platform (+13 more)

### Community 19 - "Comment Service Configuration"
Cohesion: 0.09
Nodes (21): name, redis, driver-class-name, password, url, username, ddl-auto, database-platform (+13 more)

### Community 20 - "Like Service Configuration"
Cohesion: 0.09
Nodes (21): name, redis, driver-class-name, password, url, username, ddl-auto, database-platform (+13 more)

### Community 21 - "View Service Configuration"
Cohesion: 0.09
Nodes (21): name, redis, driver-class-name, password, url, username, ddl-auto, database-platform (+13 more)

### Community 24 - "Hot Article Service Configuration"
Cohesion: 0.11
Nodes (18): name, enable-auto-commit, group-id, key-deserializer, value-deserializer, redis, endpoints, microservice-board-article-service (+10 more)

### Community 25 - "Article Pagination API Tests"
Cohesion: 0.15
Nodes (5): ArticleReadApiTest, ParameterizedTypeReference<List<ArticleReadResponse>>, ParameterizedTypeReference<List<ArticleReadResponse>>, HotArticleApiTest, ParameterizedTypeReference<List<HotArticleResponse>>

### Community 26 - "Hot Article Ranking Storage"
Cohesion: 0.22
Nodes (5): ArticleCreatedTimeRepository, HotArticleListRepository, HotArticleScoreUpdater, HotArticleListRepositoryTest, HotArticleScoreUpdaterTest

### Community 31 - "Article API Tests"
Cohesion: 0.22
Nodes (5): ArticleApiTest, ParameterizedTypeReference<List<ArticleResponse>>, ParameterizedTypeReference<List<ArticleResponse>>, ArticleCreateRequest, ArticleUpdateRequest

### Community 32 - "Domain Services and Identifiers"
Cohesion: 0.26
Nodes (5): OutboxEventPublisher, Snowflake, ArticleLikeCount, ArticleLikeCountRepository, ArticleLikeService

### Community 33 - "Hot Article Score Calculation"
Cohesion: 0.24
Nodes (4): ArticleLikeCountRepository, ArticleViewCountRepository, HotArticleScoreCalculator, HotArticleScoreCalculatorTest

### Community 34 - "Article Creation Event Handlers"
Cohesion: 0.30
Nodes (3): ArticleCreatedEventPayload, ArticleCreatedEventHandler, ArticleCreatedEventHandler

### Community 36 - "Article Deletion Event Handlers"
Cohesion: 0.35
Nodes (3): ArticleDeletedEventPayload, ArticleDeletedEventHandler, ArticleDeletedEventHandler

### Community 37 - "Comment V2 API Tests"
Cohesion: 0.27
Nodes (4): CommentApiV2Test, ParameterizedTypeReference<List<CommentResponse>>, ParameterizedTypeReference<List<CommentResponse>>, CommentCreateRequestV2

## Knowledge Gaps
- **8 isolated node(s):** `ARTICLE_CREATED`, `ARTICLE_DELETED`, `ARTICLE_LIKED`, `ARTICLE_UNLIKED`, `ARTICLE_UPDATED` (+3 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 183 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **27 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Event` connect `Event Serialization and Publishing` to `JPA Entities and Persistence`, `Event Payloads and Consumers`, `Article Creation Event Handlers`, `Article Read Query Models`, `Article Read HTTP Clients`, `Article Deletion Event Handlers`, `Event Types and Dispatch`, `Hot Article Event Tests`, `Hot Article Ranking Storage`, `Mocked Service Unit Tests`?**
  _High betweenness centrality (0.061) - this node is a cross-community bridge._
- **What connects `ARTICLE_CREATED`, `ARTICLE_DELETED`, `ARTICLE_LIKED` to the rest of the system?**
  _8 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `JPA Entities and Persistence` be split into smaller, more focused modules?**
  _Cohesion score 0.0680517916290274 - nodes in this community are weakly interconnected._
- **Why does `CommentResponse` connect `Comment HTTP Controllers` to `JPA Entities and Persistence`, `Domain Services and Identifiers`, `Comment V2 API Tests`, `Repository and Numeric Tests`, `Comment V2 Creation`, `Article Pagination API Tests`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `Event Payloads and Consumers` be split into smaller, more focused modules?**
  _Cohesion score 0.05997778600518327 - nodes in this community are weakly interconnected._
- **Why does `ArticleResponse` connect `JPA Entities and Persistence` to `Domain Services and Identifiers`, `Transactional Article Operations`, `Article API Tests`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `JPA Repositories and Queries` be split into smaller, more focused modules?**
  _Cohesion score 0.08287961282516637 - nodes in this community are weakly interconnected._
