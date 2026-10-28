package microservice.board.comment.service;

import lombok.RequiredArgsConstructor;
import microservice.board.comment.entity.Comment;
import microservice.board.comment.repository.CommentRepository;
import microservice.board.common.snowflake.Snowflake;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final Snowflake snowflake = new Snowflake();
    private final CommentRepository commentRepository;

    public CommentResult.Read read(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        return CommentResult.Read.from(comment);
    }

    @Transactional
    public CommentResult.Create create(CommentCommand.Create command) {
        Comment parentComment = findParentComment(command.parentCommentId());
        Comment comment = commentRepository.save(
                Comment.of(
                        snowflake.nextId(),
                        command.content(),
                        command.articleId(),
                        parentComment == null ? null : parentComment.getCommentId(),
                        command.writerId()
                )
        );
        return CommentResult.Create.from(comment);
    }

    private Comment findParentComment(Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }

        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRoot)
                .orElseThrow();
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }
                });
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);

        if (!comment.isRoot()) {
            commentRepository.findById(comment.getParentCommentId())
                    .filter(Comment::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }
}
