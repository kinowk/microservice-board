package microservice.board.comment.repository;

import microservice.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * select count(*)
     * from (
     * select comment_id
     * from comment
     * where article_id = :articleId
     * and parent_comment_id = :parentcommentId
     * limit :limit
     * ) t
     * @param articleId
     * @param parentCommentId
     * @param limit
     * @return
     */
    @Query(
            value = "select count(*) " +
                    "from (" +
                    "   select comment_id " +
                    "   from comment " +
                    "   where article_id = :articleId " +
                    "   and parent_commit_id = :parentCommentId " +
                    "   limit :limit" +
                    ") t",
            nativeQuery = true
    )
    long countBy(
            @Param("articleId") Long articleId,
            @Param("parentCommentId") Long parentCommentId,
            @Param("limit") Long limit
    );

    /**
     * select comment.*
     * from (
     * select commnet_id
     * from comment
     * where article_id = :articleId
     * limit :limit offset :offset
     * ) t left join comment on t.comment_id = comment.comment_id
     * @param articleId
     * @param offset
     * @param limit
     * @return
     */
    @Query(
            value = "select comment.commnet_id, comment.content, " +
                    "       comment.article_id, comment.parent_comment_id, comment.wrtier_id, " +
                    "       comment.deleted, comment.created_at " +
                    "from (" +
                    "   select comment_id " +
                    "   from comment " +
                    "   where article_id = :articleId " +
                    "   limit :limit offset :offset " +
                    ") t left join comment on t.comment_id = comment.comment_id",
            nativeQuery = true
    )
    List<Comment> findAll(
            @Param("articleId") Long articleId,
            @Param("offset") Long offset,
            @Param("limit") Long limit
    );

    /**
     * select count(*)
     * from (
     * select comment_id
     * from comment
     * where articleId = :articleId
     * limit :limit
     * ) t
     * @param articleId
     * @param limit
     * @return
     */
    @Query(
            value = "select count(*)" +
                    "from (" +
                    "   select comment_id" +
                    "   from comment" +
                    "   where article_id = :articleId" +
                    "   limit :limit" +
                    ") t",
            nativeQuery = true
    )
    long count(
            @Param("articleId") Long articleId,
            @Param("limit") Long limit
    );

    /**
     * select *
     * from comment
     * where article_id = :articleId
     * order by parent_comment_id asc, comment_id asc
     * limit :limit
     * @param articleId
     * @param limit
     * @return
     */
    @Query(
            value = "select comment.comment_id, comment.content, comment.article_id, comment.parent_comment_id, " +
                    "comment.writer_id, comment.deleted, comment.created_at " +
                    "from comment " +
                    "where article_id = :articleId " +
                    "order by parent_comment_id asc, comment_id asc " +
                    "limit :limit",
            nativeQuery = true
    )
    List<Comment> findAllInfiniteScroll(
            @Param("articleId") Long articleId,
            @Param("limit") Long limit
    );

    /**
     * select *
     * from comment
     * where article_id = :articleId
     * and (
     * parent_comment_id > :lastParentCommentId
     * or (parent_comment_id = :lastParentCommentId and comment_id > :lastCommentId)
     * )
     * order by parent_comment_id asc, comment_id asc
     * limit :limit
     * @param articleId
     * @param lastParentCommentId
     * @param lastCommentId
     * @param limit
     * @return
     */
    @Query(
            value = "select comment.comment_id, comment.content, comment.article_id, comment.parent_comment_id, " +
                    "comment.writer_id, comment.deleted, comment.created_at " +
                    "from comment " +
                    "where article_id = :articleId " +
                    "and ( " +
                    "parent_comment_id > :lastParentCommentId or (parent_comment_id = :lastParentCommentId and comment_id > :lastCommentId) " +
                    ")" +
                    "order by parent_comment_id asc, comment_id asc " +
                    "limit :limit",
            nativeQuery = true
    )
    List<Comment> findAllInfiniteScroll(
            @Param("articleId") Long articleId,
            @Param("lastParentCommentId") Long lastParentCommentId,
            @Param("lastCommentId") Long lastCommentId,
            @Param("limit") Long limit
    );
}
